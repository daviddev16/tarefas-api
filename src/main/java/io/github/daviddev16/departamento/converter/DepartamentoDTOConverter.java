package io.github.daviddev16.departamento.converter;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;
import io.github.daviddev16.departamento.response.DepartamentoResponseDTO;
import org.springframework.beans.BeanUtils;

public final class DepartamentoDTOConverter {

    public static DepartamentoResponseDTO converterParaResponseDTO(Departamento departamento) {
        DepartamentoResponseDTO departamentoResponseDTO = new DepartamentoResponseDTO();
        BeanUtils.copyProperties(departamento, departamentoResponseDTO);
        return departamentoResponseDTO;
    }

    public static Departamento converterParaDepartamento(CriarDepartamentoRequestDTO criarDepartamentoRequestDTO) {
        return new Departamento(criarDepartamentoRequestDTO.getNome());
    }

}
