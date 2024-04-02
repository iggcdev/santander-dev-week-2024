package com.iggcdev.sdw24.domain.model;

public record Champion(//Record, pois é necessario que todos os objetos champion possuam atributos como os da tabela no banco de dados.
                       //id, name, role, lore, image_url
                       Long id,
                       String name,
                       String role,
                       String lore,
                       String imageUrl
) {
    public String generateContextByQuestion(String question){
        return """
        Pergunta: %s
        Nome do campeão: %s
        Função: %s
        Lore: %s    
        """.formatted(question,this.name,this.role,this.lore);
    }
}
