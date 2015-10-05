# Cookies handler

The cookies-handler tool is desined to work with header and cookies information. The projects are developed with Maven.

## Dependencies

- Commons Httpclient
- Servlet API.

## How to use program?

Replace `http` or `https` with valid url:

      HttpClient client = new HttpClient();
      // Http or Https Url must be specified first
      GetMethod method = new GetMethod("...");

## Features

[**Header**](http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html) information:

- Response code
- Permissions
- Request method
- Content
- Content length
- Content type
- Error stream

[**Cookies**](https://hc.apache.org/httpclient-3.x/apidocs/org/apache/commons/httpclient/Cookie.html) information:

- Cookie name
- Cookie value
- Persistency value
- Exparation date
- Comment about cookie
