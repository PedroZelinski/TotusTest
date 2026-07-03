Projeto construído com Spring Boot 4, estruturado em camadas (Controller, Service, Repository e Mapper), 
adotando separação clara de responsabilidades e foco em escalabilidade e manutenção. 

O fluxo de importação de arquivos CSV é executado de forma assíncrona via mensageria com RabbitMQ, 
garantindo desacoplamento entre recebimento do arquivo e processamento.

Foram aplicados padrões como DTO para isolamento de domínio, validação com Bean Validation para regras de 
consistência de dados e uso de JPA com Specifications para suporte a consultas dinâmicas e filtros. 

O armazenamento de arquivos é feito em diretório local configurado, com geração de protocolo único para 
rastreabilidade do processo.

A comunicação entre serviços internos ocorre via eventos publicados em fila, permitindo processamento resiliente 
e extensível para futuras etapas como retry e dead-letter queue.

Execução do projeto

Para subir toda a aplicação (incluindo dependências como banco de dados e RabbitMQ), execute:

docker-compose up --build

O comando irá construir as imagens necessárias e iniciar os containers configurados no ambiente.

Na raiz do projeto existe uma collection com o endpoint de importação com um arquivo csv para teste.