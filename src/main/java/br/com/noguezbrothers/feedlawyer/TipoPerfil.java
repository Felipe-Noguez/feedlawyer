package br.com.noguezbrothers.feedlawyer;

public enum TipoPerfil {
    ADMINISTRADOR(1), ADVOGADO(2), CLIENTE(3);

    private Integer tipoPerfil;

    TipoPerfil(Integer tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Integer getTipoPerfil() {
        return tipoPerfil;
    }
}
