package com.springbatch.remotechunkingjob.dominio;

import java.io.Serializable;
import java.util.Date;

import org.apache.logging.log4j.util.Strings;

// Como esse tipo de otimização é diferente do particionamento, este que manda os dados do job e step manager para as workers pegarem as suas partições e processarem, no remote chunking mandamos o chunk com o                         dado já lido propriamente. Então é neccessário que essa classe seja serializável, para que a fila consiga serializar e desserializar
public class Pessoa implements Serializable {

  private static final long serialVersionUID = -4367678453768438435L;
  private int id;
  private String nome;
  private String email;
  private Date dataNascimento;
  private int idade;
  private String endereco;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(Date dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public int getIdade() {
    return idade;
  }

  public void setIdade(int idade) {
    this.idade = idade;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public boolean isValida() {
    return !Strings.isBlank(nome) && !Strings.isBlank(email) && dataNascimento != null;
  }

}
