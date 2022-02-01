import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import server.Status;

public class Mensagem implements Serializable{
    
    private String operacao;
    private Status status;

    /**
     * chave: Object
     */
    Map<String, Object> params;

    public Mensagem(String operacao)
    {
        this.operacao = operacao;
        params = new HashMap<>();
        //params.put("HELLO", 1.0); exemplo de param
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Status getStatus()
    {
        return status;    
    }

    public void setParam(String chave, Object valor)
    {
        params.put(chave, valor);
    }

    public Object getParam(String chave)
    {
        return params.get(chave);
    }

    public String getOperacao()
    {
        return operacao;
    }

    
}
