package by.vistar.entity;

public class Currency {
    private Long id;
    private String codeIso4217;
    private String codeNumberIso4217;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeIso4217() {
        return codeIso4217;
    }

    public void setCodeIso4217(String codeIso4217) {
        this.codeIso4217 = codeIso4217;
    }

    public String getCodeNumberIso4217() {
        return codeNumberIso4217;
    }

    public void setCodeNumberIso4217(String codeNumberIso4217) {
        this.codeNumberIso4217 = codeNumberIso4217;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
