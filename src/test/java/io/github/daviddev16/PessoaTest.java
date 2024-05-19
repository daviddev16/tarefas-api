package io.github.daviddev16;

import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;
import io.github.daviddev16.departamento.service.DepartamentoService;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.PessoaDepartamento;
import io.github.daviddev16.pessoa.dto.request.AlterarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.request.CriarPessoaRequestDTO;
import io.github.daviddev16.pessoa.exception.PessoaNaoEncontradaException;
import io.github.daviddev16.pessoa.service.PessoaDepartamentoService;
import io.github.daviddev16.pessoa.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PessoaTest {

    private @Autowired PessoaService pessoaService;
    private @Autowired DepartamentoService departamentoService;
    private @Autowired PessoaDepartamentoService pessoaDepartamentoService;

    @Test
    @DisplayName("Alteração Pessoa Por Id")
    void validarExlusaoDepartamentoPorNomeTest() {
        Pessoa pessoa = pessoaService
                .criarPessoa(new CriarPessoaRequestDTO("PESSOA TESTE 1"));

        Assertions.assertNotNull(pessoa);
        Long pessoaId = pessoa.getId();

        Pessoa pessoaAlterada = pessoaService
                .alterarPessoaPorId(pessoaId, new AlterarPessoaRequestDTO("PESSOA TESTE ALTERADO"));

        Assertions.assertNotNull(pessoaAlterada);
        Assertions.assertEquals(pessoaId, pessoaAlterada.getId());
        Assertions.assertNotEquals(pessoaAlterada.getNome(), pessoa.getNome());
    }

    @Test
    @DisplayName("Excluir Pessoa Por Id")
    void validarExlusaoPessoaPorIdTest() {
        Pessoa pessoa = pessoaService
                .criarPessoa(new CriarPessoaRequestDTO("PESSOA EXCLUIR"));

        Assertions.assertNotNull(pessoa);
        Long pessoaId = pessoa.getId();

        pessoaService.excluirPessoaPorId(pessoaId);

        Assertions.assertThrows(PessoaNaoEncontradaException.class, () ->
                pessoaService.obterPessoaPorId(pessoaId));
    }

    @Test
    @DisplayName("Não Permite Pessoa com Nome Duplicado")
    void validarNomePessoaUnicoTest() {
        pessoaService.criarPessoa(new CriarPessoaRequestDTO("PESSOA TESTE"));
        Assertions.assertThrows(ServicoException.class, () ->
                pessoaService.criarPessoa(new CriarPessoaRequestDTO("PESSOA TESTE")));
    }

    @Test
    @DisplayName("Vincular departamento em Pessoa Por Id")
    void validarVincularDepartamentoEmPessoaPorIdTest() {
        Pessoa pessoa = pessoaService
                .criarPessoa(new CriarPessoaRequestDTO("PESSOA2"));

        Departamento departamento = departamentoService
                .criarDepartamento(new CriarDepartamentoRequestDTO("DEPARTAMENTO01"));

        Assertions.assertNotNull(pessoa);
        Assertions.assertNotNull(departamento);

        AtomicReference<Pessoa> pessoaComDepartamento = new AtomicReference<>();

        Assertions.assertDoesNotThrow(() -> {
            pessoaComDepartamento.set(pessoaDepartamentoService
                    .vincularDepartamentoEmPessoa(pessoa, departamento));
        });

        boolean departamentoPresente = pessoaComDepartamento.get()
                .getPessoaDepartamentos()
                .stream()
                .map(PessoaDepartamento::getDepartamento)
                .anyMatch(dep -> dep.getNome().equals("DEPARTAMENTO01"));

        Assertions.assertTrue(departamentoPresente);
    }

    @Test
    @DisplayName("Desvincular departamento em Pessoa Por Id")
    void validarDesvincularDepartamentoEmPessoaPorIdTest() {
        Pessoa pessoa = pessoaService
                .criarPessoa(new CriarPessoaRequestDTO("PESSOA3"));

        Departamento departamento = departamentoService
                .criarDepartamento(new CriarDepartamentoRequestDTO("DEPARTAMENTO02"));

        Assertions.assertNotNull(pessoa);
        Assertions.assertNotNull(departamento);

        pessoaDepartamentoService.vincularDepartamentoEmPessoa(pessoa, departamento);

        Assertions.assertDoesNotThrow(() ->
                pessoaDepartamentoService.desvincularDepartamentoDePessoa(pessoa, departamento));

        boolean departamentoNaoPresente = pessoa
                .getPessoaDepartamentos()
                .stream()
                .map(PessoaDepartamento::getDepartamento)
                .noneMatch(dep -> dep.getNome().equals("DEPARTAMENTO02"));

        Assertions.assertTrue(departamentoNaoPresente);
    }

}

