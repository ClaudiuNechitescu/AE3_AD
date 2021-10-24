package es.florida.AE3_AD;

import java.time.Year;

public class Llibre {
	private Integer identificador;
    private String titol;
    private String autor;
    private Integer anyPublicacio;
    private String editorial;
    private Integer nombrePagines;

    public Llibre() {};
    
    public Llibre(int id,String tit,String aut,int any,String edi,int nom){
        this.identificador=id;
        this.titol=tit;
        this.autor=aut;
        this.anyPublicacio=any;
        this.editorial=edi;
        this.nombrePagines=nom;
    }


	public Integer getId(){
        return identificador;
    }
    
    public String getTitol(){
        return titol;
    }

    public String getAutor(){
        return autor;
    }

    public Integer getAnyPublicacio(){
        return anyPublicacio;
    }

    public String getEditorial(){
        return editorial;
    }

    public int getNombrePagines(){
        return nombrePagines;
    }

    public void setId(Integer id){
        identificador=id;
    }
    
    public void setTitol(String tit){
        titol = tit;
    }

    public void setAutor(String aut){
        autor=aut;
    }

    public void setAnyPublicacio(Integer any){
        anyPublicacio=any;
    }

    public void setEditorial(String edi){
        editorial=edi;
    }

    public void setNombrePagines(Integer nom){
        nombrePagines=nom;
    }

}
