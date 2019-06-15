package br.ufms.ed.projeto;

public class Memoria {
    private int memoriaLivre;
    private EspacoMemoria inicio;

    public Memoria(){
        this.memoriaLivre = 4096;
        this.inicio = new EspacoMemoria(4096, 0);
    }

    public void reservarMemoria(int tamanho){

        this.validaTamanhoReserva(tamanho);

        EspacoMemoria ptr = this.inicio;
        EspacoMemoria anterior = null;

        while (ptr!=null){
            if(ptr.getTamanho()>=tamanho){
                ptr.setEndereco(ptr.getEndereco()+tamanho);
                ptr.setTamanho(ptr.getTamanho()-tamanho);

                if(ptr.getTamanho()==0){
                    if(anterior==null){
                        this.inicio=ptr.getProxima();
                    } else {
                        anterior.setProximo(ptr.getProxima());
                    }
                }
                return;
            }

            anterior=ptr;
            ptr=ptr.getProxima();

        }
        System.out.println("Não foi possivel reservar a memoria");
    }

    public void devolveMemoria(int tamanho, int endereco){
        this.validaEndereco(endereco);
        this.validaTamanho(tamanho);

        if(this.inicio==null){
            this.inicio = new EspacoMemoria(tamanho, endereco);
            this.memoriaLivre+=tamanho;

            return;
        }

        EspacoMemoria ptr = this.inicio;
        EspacoMemoria anterior = null;
        while (ptr!=null){

            if(ptr.getEndereco()>=endereco+tamanho){
                if(ptr.getEndereco()==endereco+tamanho){
                    EspacoMemoria novo = new EspacoMemoria(ptr, tamanho, endereco);
                    this.inicio = novo;
                    this.mergeMemoria(novo, ptr);
                    return;
                }

                EspacoMemoria novo = new EspacoMemoria(ptr, tamanho, endereco);
                if(anterior==null) {
                    this.inicio = novo;
                    this.mergeMemoria(novo, novo.getProxima());
                }
                else{
                    anterior.setProximo(novo);
                    this.mergeMemoria(anterior, novo);
                }

                return;
            }
            anterior=ptr;
            ptr=ptr.getProxima();
        }

        if(anterior.getFim()<=endereco){

            EspacoMemoria novo = new EspacoMemoria(anterior.getProxima(), tamanho, endereco);
            this.mergeMemoria(anterior, novo);
            if(novo.getProxima()!=null){
                this.mergeMemoria(novo, novo.getProxima());
            }

            return;
        }

        System.out.println("Não foi possivel devolver memoria");
    }

    private void mergeMemoria(EspacoMemoria comeco, EspacoMemoria calda){
        if(comeco.getFim()==calda.getEndereco()){
            comeco.setTamanho(comeco.getTamanho()+calda.getTamanho());
            comeco.setProximo(calda.getProxima());
        } else {
            comeco.setProximo(calda);
        }
    }

    public String toString(){
        StringBuilder retorno = new StringBuilder();
        EspacoMemoria ptr = this.inicio;

        while (ptr!=null){
            retorno.append("->");
            retorno.append(ptr.toString());
            ptr = ptr.getProxima();
        }

        retorno.append("->");

        return retorno.toString();
    }

    private void validaTamanho(int tamanho){
        if(tamanho<=0)
            throw new IllegalArgumentException("Tamanho não pode ser menor ou igual a zero");

        if(tamanho>4096)
            throw new IllegalArgumentException("Tamanho maior que memoria");
    }

    private void validaTamanhoReserva(int tamanho){
        if(this.inicio==null)
            throw new IllegalArgumentException("Memoria cheia");

        if(tamanho<=0)
            throw new IllegalArgumentException("Tamanho não pode ser menor ou igual a zero");

        if(tamanho>this.memoriaLivre)
            throw new IllegalArgumentException("Tamanho maior que memoria disponivel");
    }

    private void validaEndereco(int endereco){
        if(endereco<0 || endereco>4095)
            throw new IllegalArgumentException("Endereço de memoria invalido!");
    }

    public String imprimirMemoriaOcupada(){
        StringBuilder saida = new StringBuilder();
        if(this.inicio==null){
            saida.append("->[0h , 4096]->");
        } else {
           if(this.inicio.getEndereco()>0){
               saida.append("->[0h , ");
               saida.append(this.inicio.getEndereco());
               saida.append("]->");
           }

           EspacoMemoria ptr = this.inicio.getProxima();
           EspacoMemoria anterior = this.inicio;
           while (ptr!=null){
               saida.append("[");
               saida.append(Integer.toHexString(anterior.getFim())+"h , ");
               saida.append(ptr.getEndereco()-anterior.getFim());
               saida.append("]->");

               anterior = ptr;
               ptr = ptr.getProxima();
           }

           if(anterior.getFim()<4096){
               saida.append("[");
               saida.append(Integer.toHexString(anterior.getFim()));
               saida.append("h , ");
               saida.append(4096-anterior.getFim());
               saida.append("]->");
           }

        }

        return saida.toString();
    }

}
