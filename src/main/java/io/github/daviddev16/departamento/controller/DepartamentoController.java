package io.github.daviddev16.departamento.controller;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.core.ApiMensagem;
import io.github.daviddev16.departamento.converter.DepartamentoDTOConverter;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;
import io.github.daviddev16.departamento.response.DepartamentoResponseDTO;
import io.github.daviddev16.departamento.service.DepartamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/departamento")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepartamentoResponseDTO> obterListaDepartamentos() {
        return departamentoService
                .obterDepartamentos().stream()
                .map(DepartamentoDTOConverter::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{departamentoId}")
    @ResponseStatus(HttpStatus.FOUND)
    public DepartamentoResponseDTO obterDepartamentoPorId(@PathVariable("departamentoId") Long id) {
        Departamento departamento = departamentoService.obterDepartamentoPorId(id);
        return DepartamentoDTOConverter.converterParaResponseDTO(departamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartamentoResponseDTO criarDepartamento(@RequestBody CriarDepartamentoRequestDTO criarDepartamentoRequestDTO) {
        Departamento departamento = departamentoService.criarDepartamento(criarDepartamentoRequestDTO);
        return DepartamentoDTOConverter.converterParaResponseDTO(departamento);
    }

    @DeleteMapping("/{departamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiMensagem excluirDepartamento(@PathVariable("departamentoId") Long id) {
        departamentoService.excluirDepartamentoPorId(id);
        return ApiMensagem.mensagem("Departamento #%d excluído com sucesso.", id);
    }

}
