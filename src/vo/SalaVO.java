package vo;

/**
 *
 * @author maikon
 */
public class SalaVO {
    
    private String id;
    private int x;
    private int y;
    private int pos;
    private String region;
    private String[] vizinhos;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
/**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * @return the pos
     */
    public int getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(int pos) {
        this.pos = pos;
    }
    
    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }
    
    /**
     * @return the vizinhos
     */
    public String[] getVizinhos() {
        return vizinhos;
    }

    /**
     * @param vizinhos the vizinhos to set
     */
    public void setVizinhos(String[] vizinhos) {
        this.vizinhos = vizinhos;
    }
}
