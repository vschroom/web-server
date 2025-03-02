package com.chvs.webserverdemo.http;

import java.io.IOException;
import java.io.InputStream;

public interface HttpRequestParser {

    HttpRequest parse(InputStream requestData) throws IOException;
}
