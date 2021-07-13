package vo;

/**
 *
 * @author maikon
 */
public class SalaVO {
    private String id;
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
