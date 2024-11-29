package com.br.SGP.base;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

public abstract class BaseService <Model extends BaseModel, Repository extends BaseRepository<Model>> {

    @Autowired
    protected Repository repository;

    /*
     * Busca todos os itens registraados no banco de dados
     * @param nenhum
     * @return List<Model> - retorna uma lista contendo todos os itens registrados no banco de dados
     * */
    public List<Model> findAll(org.springframework.data.domain.Pageable pageable){
        return repository.findAll();
    }

    /*
     * Busca paginada de todos os itens registraados no banco de dados
     * @param pageable - objeto que representa um pagina
     * @return Page<Model> - retorna uma pagina de itens registrados no banco de dados
     * */
    public Page<Model> findAllPageable(Pageable pageable){
        return repository.findAll(pageable);
    }

    /*
     * Busca um item pelo seu id no banco de dados
     * @param id - identificador unico do objeto
     * @return Model - retorna o objeto caso ele exista no banco de dados
     * */
    public Model findById(String id){
        Model model = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
        return model;
    }

    /*
     * Registra um item no banco de dados
     * @param m - objeto que sera criado
     * @return Model - objeto apos sua criacao
     * */
    @Transactional
    public Model create(Model m){
        return repository.save(m);
    }

    /*
     * Atuliza um item, caso ele exista no banco de dados
     * @param m - objeto que sera atualizado
     * @return Model - objeto apos a atualizacao
     * */
    @Transactional
    public Model update(Model m){
        Model model = this.findById(m.getId());
        if(model != null){
            System.out.println("ACHOU NO FIND BY ID");
            return repository.save(m);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!");
        }
    }

    /*
     * Deleta um item, caso ele exista no banco de dados
     * @param id - identificador unico do model
     * @return nenhum
     * */
    @Transactional
    public void delete(String id){
        Model model = this.findById(id);
        if(model != null){
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Não foi possível deletar item!");
        }
    }

}

