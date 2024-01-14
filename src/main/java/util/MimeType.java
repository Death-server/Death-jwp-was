package util;

public enum MimeType {

    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    ICO(".ico", "image/x-icon")
    ,WOFF(".woff", "font/woff"),
    TTF(".ttf", "font/ttf");

    private final String extension;
    private final String mimeHeader;

    MimeType(String extension, String mimeHeader) {
        this.extension = extension;
        this.mimeHeader = mimeHeader;
    }

    public static String applyMimeHeader(String url) {
        final String NO_APPLY_MIME_HEADER_EXCEPTION = "처리 가능한 확장자가 아님";

        for(MimeType mimeType : MimeType.values()) {
            if(url.endsWith(mimeType.getExtension())) {
                return mimeType.getMimeHeader();
            }
        }

        throw new RuntimeException(NO_APPLY_MIME_HEADER_EXCEPTION + " : " + url);
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeHeader() {
        return mimeHeader;
    }
}