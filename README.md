# java-tiff2pdf

### Getting Started

To begin the service run `./start.sh ubic`

You can set the `TIFF2PDF_SERVICE_LISTEN` environment variable to change the port
number the service listens on.

### TIFF to PDF request example

To convert a TIFF to PDF, `POST` the TIFF bytes as the request body to the `/convert` endpoint.

You can set PDF metadata using the headers:
* `PDF-Subject`
* `PDF-Author`
* `PDF-Creator`
* `PDF-Title`
* `PDF-PageSize`
* `PDF-FullPage`

Log messages for a request are linked together using a randomly generated unique ID.
You can set the `X-Request-ID` header to override the default random ID.

#### Example request

```
POST /convert HTTP/1.1
Content-Type: image/tiff
PDF-Subject: pdf subject line
PDF-Author: pdf author
PDF-Creator: pdf creator
PDF-Title: pdf title
PDF-PageSize: A4
PDF-FullPage: true
X-Request-ID: my-unique-id

[TIFF bytes]
```

#### Example response

```
HTTP/1.1 200 Ok
Content-Type: application/pdf
Content-Length: [n]
PDF-Pages: [n]

[PDF bytes]
```
