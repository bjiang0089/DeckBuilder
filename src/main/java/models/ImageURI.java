package models;

public class ImageURI {
    private String small;
    private String normal;
    private String large;
    private String png;
    private String art_crop;
    private String boarder_crop;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public String getArt_crop() {
        return art_crop;
    }

    public void setArt_crop(String art_crop) {
        this.art_crop = art_crop;
    }

    public String getBoarder_crop() {
        return boarder_crop;
    }

    public void setBoarder_crop(String boarder_crop) {
        this.boarder_crop = boarder_crop;
    }
}
