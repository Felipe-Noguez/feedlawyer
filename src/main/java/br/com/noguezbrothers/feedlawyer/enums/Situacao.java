package br.com.noguezbrothers.feedlawyer.enums;

public enum Situacao {
    INATIVO(0), ATIVO(1);

    private Integer situacao;

    Situacao(Integer situacao) {
        this.situacao = situacao;
    }

    public Integer getSituacao() {
        return situacao;
    }
}
