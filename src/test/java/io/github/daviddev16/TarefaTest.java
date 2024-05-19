package io.github.daviddev16;

import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;
import io.github.daviddev16.departamento.service.DepartamentoService;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.PessoaDepartamento;
import io.github.daviddev16.pessoa.PessoaTarefa;
import io.github.daviddev16.pessoa.dto.request.AlterarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.request.CriarPessoaRequestDTO;
import io.github.daviddev16.pessoa.exception.PessoaNaoEncontradaException;
import io.github.daviddev16.pessoa.service.PessoaDepartamentoService;
import io.github.daviddev16.pessoa.service.PessoaService;
import io.github.daviddev16.tarefa.StatusTarefa;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.dto.request.CriarTarefaRequestDTO;
import io.github.daviddev16.tarefa.service.TarefaAlocacaoService;
import io.github.daviddev16.tarefa.service.TarefaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TarefaTest {

    private @Autowired TarefaService tarefaService;
    private @Autowired TarefaAlocacaoService tarefaAlocacaoService;
    private @Autowired DepartamentoService departamentoService;
    private @Autowired PessoaService pessoaService;
    private @Autowired PessoaDepartamentoService pessoaDepartamentoService;

    @Test
    @DisplayName("Criar tarefa")
    void validarCriacaoDeTarefaTest() {
        Tarefa tarefa = tarefaService.criarTarefa(
                criarTarefaSimples("TITULO TAREFA05", "TITULO DESCRIÇÃO05"));

        Assertions.assertNotNull(tarefa);
    }

    @Test
    @DisplayName("Validar criação de tarefas com nome unico")
    void validarCriacaoDeTarefaComNomeUnicoTest() {
        tarefaService.criarTarefa(
                criarTarefaSimples("Nome unico", "Descrição"));
        Assertions.assertThrows(DataIntegrityViolationException.class, () ->
                tarefaService.criarTarefa(criarTarefaSimples("Nome unico", "Descrição")));
    }

    @Test
    @DisplayName("Finalizar tarefa aberta")
    void validarFinalizacaoDeTarefaAbertaTest() {
        Tarefa tarefa = tarefaService.criarTarefa(
                criarTarefaSimples("TITULO TAREFA03", "TITULO DESCRIÇÃO03"));

        Assertions.assertNotNull(tarefa);

        tarefa = tarefaService.encerrarTarefaPorId(tarefa.getId());
        Assertions.assertEquals(tarefa.getStatusTarefa(), StatusTarefa.FINALIZADA);
    }

    @Test
    @DisplayName("Alocar pessoa para tarefa")
    void validarAlocarPessoaParaTarefaTest() {

        Departamento departamento = departamentoService
                .criarDepartamento(new CriarDepartamentoRequestDTO("TEST"));

        Tarefa tarefa = tarefaService.criarTarefa(
                criarTarefaSimples("TITULO TAREFA02", "TITULO DESCRIÇÃO02",
                        Set.of(departamento.getNome())));

        Pessoa pessoa = pessoaService
                .criarPessoa(new CriarPessoaRequestDTO("PessoaTeste"));

        Assertions.assertNotNull(tarefa);
        Assertions.assertNotNull(pessoa);
        Long pessoaId = pessoa.getId();

        pessoa = pessoaDepartamentoService.vincularDepartamentoEmPessoa(pessoa, departamento);

        pessoaService.alocarPessoaEmTarefaPorId(pessoaId, tarefa.getId());

        boolean existeTarefaEmPessoa = pessoaService.obterPessoaPorId(pessoaId)
                .getPessoaTarefas()
                .stream()
                .map(PessoaTarefa::getTarefa)
                .anyMatch(tarefa1 -> tarefa1.getTitulo().equals("TITULO TAREFA02"));

        Assertions.assertTrue(existeTarefaEmPessoa);
    }

    @Test
    @DisplayName("Obter tarefas pendentes")
    void validarObterTarefasPendentesTest() {
        /* tarefa sem pessoa alocada */
        tarefaService.criarTarefa(criarTarefaSimplesAntiga("Tarefa1", "descrição"));
        tarefaService.criarTarefa(criarTarefaSimplesAntiga("Tarefa2", "descrição"));
        tarefaService.criarTarefa(criarTarefaSimplesAntiga("Tarefa3", "descrição"));
        List<Tarefa> tarefasPendentes = tarefaService.obterTarefasPendentes(3);
        Assertions.assertEquals(3, tarefasPendentes.size());
    }

    private CriarTarefaRequestDTO criarTarefaSimples(String titulo, String descricao, Set<String> departamentos) {
        return CriarTarefaRequestDTO
                .builder()
                    .titulo(titulo)
                    .descricao(descricao)
                    .dataPrazo(LocalDateTime.now().plusMonths(2))
                    .departamentos(departamentos)
                .build();
    }

    private CriarTarefaRequestDTO criarTarefaSimplesAntiga(String titulo, String descricao) {
        return CriarTarefaRequestDTO
                .builder()
                .titulo(titulo)
                .descricao(descricao)
                .dataPrazo(LocalDateTime.now().plusMinutes(1))
                .build();
    }

    private CriarTarefaRequestDTO criarTarefaSimples(String titulo, String descricao) {
        return criarTarefaSimples(titulo, descricao, Collections.emptySet());
    }

}

