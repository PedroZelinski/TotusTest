package com.br.TotusTest.DTOs;

import jakarta.validation.constraints.NotBlank;

public record FornecedorDTO(
		Long id, 
		
		@NotBlank(message = "Nome é obrigatório")
		String nome	)
	{

}
