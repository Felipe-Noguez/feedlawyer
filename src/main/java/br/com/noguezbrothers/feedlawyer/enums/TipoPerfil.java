package br.com.noguezbrothers.feedlawyer.enums;

public enum TipoPerfil {
    ADMINISTRADOR(0), ADVOGADO(1), CLIENTE(2);

    private Integer tipoPerfil;

    TipoPerfil(Integer tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Integer getTipoPerfil() {
        return tipoPerfil;
    }
}
