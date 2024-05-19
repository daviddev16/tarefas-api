package io.github.daviddev16;

import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.exception.DepartamentoNaoEncontradoException;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;
import io.github.daviddev16.departamento.service.DepartamentoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class DepartamentoTest {

    private @Autowired DepartamentoService departamentoService;

    @Test
    @DisplayName("Criar departamento")
    void validarCriacaoDepartamentoPorNomeTest() {
        Departamento departamento = departamentoService
                .criarDepartamento(new CriarDepartamentoRequestDTO("CriacaoDepartamento"));
        Assertions.assertNotNull(departamento);
    }

    @Test
    @DisplayName("Excluir departamento por Id")
    void validarExlusaoDepartamentoPorNomeTest() {
        Departamento departamento = departamentoService
                .criarDepartamento(new CriarDepartamentoRequestDTO("TestDepartamento"));

        Long departamentoId = departamento.getId();

        Assertions.assertDoesNotThrow(() ->
            departamentoService.excluirDepartamentoPorId(departamentoId));

        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,  () ->
                departamentoService.obterDepartamentoPorId(departamentoId));
    }

    @Test
    @DisplayName("Não Permite Departamento com Nome Duplicado")
    void validarNomeDepartamentoUnicoTest() {
        departamentoService.criarDepartamento(new CriarDepartamentoRequestDTO("Departamento1"));
        Assertions.assertThrows(ServicoException.class, () ->
                departamentoService.criarDepartamento(new CriarDepartamentoRequestDTO("Departamento1")));
    }

    @Test
    @DisplayName("Obter Departamento cadastrado por Nome")
    void obterDepartamentoCadastradoPorNome() {
        departamentoService.criarDepartamento(new CriarDepartamentoRequestDTO("Departamento5"));
        Departamento departamento = departamentoService.obterDepartamentoPorNome("Departamento5");
        Assertions.assertNotNull(departamento);
        Assertions.assertEquals(departamento.getNome(), "Departamento5");
    }
}

