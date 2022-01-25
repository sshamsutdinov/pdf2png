<dl>
<dt><h3>Description:</h3></dt>
<dd>This is a simple api for converting single pdf document to zip file that contains images in png format.</dd>
<hr>
<dt><h3>Endpoints:</h3></dt>
<dd>POST: /api/pdf2png</dd>
<dd>POST: /apiv2/pdf2png - without buffering response</dd>
<dd>GET: /api/test</dd>
<dt><h3>How to build Docker image:</h3></dt>
<hr>

`docker build -t salsh/pdf2png:latest`

`docker run -p 8080:8080 -t salsh97/pdf2png:latest`
</dl>
