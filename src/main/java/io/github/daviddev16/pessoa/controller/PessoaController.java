package io.github.daviddev16.pessoa.controller;

import io.github.daviddev16.core.ApiMensagem;
import io.github.daviddev16.core.Utilitarios;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.converter.PessoaDTOConverter;
import io.github.daviddev16.pessoa.dto.OpcoesBuscaPessoaDTO;
import io.github.daviddev16.pessoa.dto.request.AlterarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.request.CriarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaGastoResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaResponseDTO;
import io.github.daviddev16.pessoa.service.PessoaDepartamentoService;
import io.github.daviddev16.pessoa.service.PessoaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaDepartamentoService pessoaDepartamentoService;

    public PessoaController(PessoaService pessoaService,
                            PessoaDepartamentoService pessoaDepartamentoService) {
        this.pessoaService = pessoaService;
        this.pessoaDepartamentoService = pessoaDepartamentoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PessoaMetricaResponseDTO> obterListaPessoas() {
        return pessoaService.obterListaPessoaMetrica();
    }

    @GetMapping("/{pessoaId}")
    @ResponseStatus(HttpStatus.FOUND)
    public PessoaResponseDTO obterPessoaPorId(@PathVariable("pessoaId") Long id) {
        Pessoa pessoa = pessoaService.obterPessoaPorId(id);
        return PessoaDTOConverter.converterParaResponseDTO(pessoa);
    }

    @GetMapping("/gastos")
    @ResponseStatus(HttpStatus.FOUND)
    public List<PessoaMetricaGastoResponseDTO> obterMetricaGastoPessoa(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) @DateTimeFormat(pattern = Utilitarios.DATE_FORMAT) LocalDate dataCriacaoInicial,
            @RequestParam(required = false) @DateTimeFormat(pattern = Utilitarios.DATE_FORMAT) LocalDate dataCriacaoFinal) {
        OpcoesBuscaPessoaDTO opcoesBuscaPessoaDTO = OpcoesBuscaPessoaDTO
                .builder()
                    .nome(nome)
                    .dataCriacaoFinal(dataCriacaoFinal)
                    .dataCriacaoInicial(dataCriacaoInicial)
                .build();
        return pessoaService.obterListaPessoaMetricaGasto(opcoesBuscaPessoaDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponseDTO criarPessoa(@RequestBody CriarPessoaRequestDTO criarPessoaRequestDTO) {
        Pessoa pessoa = pessoaService.criarPessoa(criarPessoaRequestDTO);
        return PessoaDTOConverter.converterParaResponseDTO(pessoa);
    }

    @PostMapping("/{pessoaId}/departamento/{departamentoId}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponseDTO vincularDepartamentoPorId(@PathVariable("pessoaId") Long pessoaId,
                                                       @PathVariable("departamentoId") Long departamentoId) {
        pessoaDepartamentoService.vincularDepartamentoEmPessoa(pessoaId, departamentoId);
        Pessoa pessoa = pessoaService.obterPessoaPorId(pessoaId);
        return PessoaDTOConverter.converterParaResponseDTO(pessoa);
    }

    @DeleteMapping("/{pessoaId}/departamento/{departamentoId}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponseDTO desvincularDepartamentoPorId(@PathVariable("pessoaId") Long pessoaId,
                                                          @PathVariable("departamentoId") Long departamentoId) {
        pessoaDepartamentoService.desvincularDepartamentoDePessoa(pessoaId, departamentoId);
        Pessoa pessoa = pessoaService.obterPessoaPorId(pessoaId);
        return PessoaDTOConverter.converterParaResponseDTO(pessoa);
    }

    @PutMapping("/{pessoaId}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponseDTO alterarPessoaPorId(@PathVariable("pessoaId") Long id,
                                                @RequestBody AlterarPessoaRequestDTO alterarPessoaRequestDTO) {
        Pessoa alteradaPessoa = pessoaService.alterarPessoaPorId(id, alterarPessoaRequestDTO);
        return PessoaDTOConverter.converterParaResponseDTO(alteradaPessoa);
    }

    @DeleteMapping("/{pessoaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiMensagem excluirPessoa(@PathVariable("pessoaId") Long id) {
        pessoaService.excluirPessoaPorId(id);
        return ApiMensagem.mensagem("Pessoa #%d excluído com sucesso.", id);
    }

}
