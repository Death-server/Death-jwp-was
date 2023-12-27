package util;

public enum MimeType {

    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    ICO(".ico", "image/x-icon")
    ,WOFF(".woff", "font/woff");

    final String extension;
    final String mimeHeader;

    MimeType(String extension, String mimeHeader) {
        this.extension = extension;
        this.mimeHeader = mimeHeader;
    }


    public String getExtension() {
        return extension;
    }

    public String getMimeHeader() {
        return mimeHeader;
    }
}
