package br.ufms.ed.projeto;

public class EspacoMemoria {
    private EspacoMemoria proximo;
    private int endereco;
    private int tamanho;

    public EspacoMemoria(EspacoMemoria proximo, int tamanho, int endereco){
        this.proximo = proximo;
        this.endereco = endereco;
        this.tamanho = tamanho;
    }

    public EspacoMemoria(int tamanho, int endereco ){
        this.endereco = endereco;
        this.tamanho = tamanho;
        this.proximo = null;
    }

    public void setProximo(EspacoMemoria proximo){
        this.proximo = proximo;
    }

    public void setEndereco(int endereco){
        this.endereco = endereco;
    }

    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }

    public EspacoMemoria getProxima(){
        return this.proximo;
    }

    public int getEndereco(){
        return this.endereco;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public int getFim(){
        return this.endereco+this.tamanho;
    }

    public String toString(){
        StringBuilder retorno = new StringBuilder();
        retorno.append("["+ Integer.toHexString(this.endereco)+"h , " + this.tamanho +"]");
        return retorno.toString();
    }

}
