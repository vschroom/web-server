package com.chvs.webserverdemo.http;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequestParser {

    private static final StringBuilder BUFFER = new StringBuilder();
    private static final StringBuilder KEY_BUFFER = new StringBuilder();
    private static final StringBuilder VALUE_BUFFER = new StringBuilder();
    private static final int SP = 32;
    private static final int CR = 13;
    private static final int LF = 10;
    private static final int CL = 58;
    private static final int Q = 63;
    private static final int EQ = 61;
    private static final int AND = 38;

    public void parse(HttpRequest httpRequest, HttpResponse httpResponse, InputStream requestData) throws IOException {
        try {
            parseStartLine(httpRequest, requestData);
            parseHeaders(httpRequest, requestData);
            parseBody(httpRequest, requestData);
        } catch (HttpParseRequestException pex) {
            httpResponse.setStatusCode(pex.getStatusCode());
        }
    }

    private void parseStartLine(HttpRequest httpRequest, InputStream inputData) throws HttpParseRequestException, IOException {
        boolean methodParsed = false;
        boolean pathParsed = false;
        boolean pathParamsParsed = false;
        boolean keyBufferParsed = true;

        int readByte;
        while ((readByte = inputData.read()) != -1) {
            if (readByte == CR) {
                readByte = inputData.read();
                if (readByte == LF) {
                    httpRequest.setVersion(BUFFER.toString());
                    BUFFER.delete(0, BUFFER.length());
                    break;
                }
                throw new HttpParseRequestException();
            }
            if (readByte == SP) {
                if (!methodParsed) {
                    httpRequest.setMethod(BUFFER.toString());
                    methodParsed = true;
                } else if (!pathParsed) {
                    httpRequest.setPath(BUFFER.toString());
                    pathParsed = true;
                } else if (!pathParamsParsed) {
                    httpRequest.getPathParams().put(KEY_BUFFER.toString(), VALUE_BUFFER.toString());
                    KEY_BUFFER.delete(0, KEY_BUFFER.length());
                    VALUE_BUFFER.delete(0, VALUE_BUFFER.length());
                    pathParamsParsed = true;
                } else {
                    throw new HttpParseRequestException();
                }
                BUFFER.delete(0, BUFFER.length());
            } else if (methodParsed && readByte == Q) {
                httpRequest.setPath(BUFFER.toString());
                pathParsed = true;
                BUFFER.delete(0, BUFFER.length());
            } else if (pathParsed && !pathParamsParsed) {
                if (readByte == EQ) {
                    keyBufferParsed = false;
                } else if (readByte == AND) {
                    keyBufferParsed = true;
                    httpRequest.getPathParams().put(KEY_BUFFER.toString(), VALUE_BUFFER.toString());
                    KEY_BUFFER.delete(0, KEY_BUFFER.length());
                    VALUE_BUFFER.delete(0, VALUE_BUFFER.length());
                } else {
                    if (keyBufferParsed) {
                        KEY_BUFFER.append((char) readByte);
                    } else {
                        VALUE_BUFFER.append((char) readByte);
                    }
                }
                BUFFER.delete(0, BUFFER.length());
            } else {
                BUFFER.append((char) readByte);
            }
        }
    }

    private void parseHeaders(HttpRequest httpRequest, InputStream inputData) throws IOException {
        boolean keyParsed = true;
        int readByte;
        while ((readByte = inputData.read()) != -1) {
            if (readByte == CR) {
                readByte = inputData.read();
                if (readByte == LF) {
                    if (!KEY_BUFFER.isEmpty() && !VALUE_BUFFER.isEmpty()) {
                        httpRequest.getHeaders().put(KEY_BUFFER.toString(), VALUE_BUFFER.toString());
                        KEY_BUFFER.delete(0, KEY_BUFFER.length());
                        VALUE_BUFFER.delete(0, VALUE_BUFFER.length());
                        keyParsed = true;
                    } else {
                        break;
                    }
                } else {
                    throw new HttpParseRequestException();
                }
            } else {
                if (readByte == CL) {
                    var temp = readByte;
                    readByte = inputData.read();
                    if (readByte == SP) {
                        keyParsed = false;
                    } else {
                        if (keyParsed) {
                            KEY_BUFFER.append((char) temp);
                            KEY_BUFFER.append((char) readByte);
                        } else {
                            VALUE_BUFFER.append((char) temp);
                            VALUE_BUFFER.append((char) readByte);
                        }
                    }
                } else {
                    if (keyParsed) {
                        KEY_BUFFER.append((char) readByte);
                    } else {
                        VALUE_BUFFER.append((char) readByte);
                    }
                }
            }
        }
    }

    private void parseBody(HttpRequest httpRequest, InputStream inputData) throws IOException {
        var contentTypeValue = httpRequest.getHeaders().get(HttpHeader.CONTENT_LENGTH.getHeaderName());
        if (contentTypeValue != null) {
            var content = new byte[Integer.parseInt(contentTypeValue)];
            var res = inputData.read(content);
            if (res == -1) {
                throw new HttpParseRequestException();
            }
            httpRequest.setBody(content);
        }
    }
}
