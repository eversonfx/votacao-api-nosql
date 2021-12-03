<h1 align="center"> Votação API REST 📊</h1>
<p align="center">Aplicação REST para votação em Assembléia</p>



## :open_book: Objetivo: </h2>

- [x] Cadastrar uma nova pauta;
- [x] Abrir uma seção de votação em uma pauta; 
- [x] A seção deve ficar aberta pelo tempo determinado, caso não seja informado ficará aberta pelo tempo padrão de 1 minuto;
- [x] Receber votos dos associados em pauta;
- [x] Os votos são SIM ou NÃO;
- [x] Cada associado possui um id único;
- [x] Os associados podem votar uma só vez por pauta;
- [x] Contabilizar os votos e dar o resultado da votação na pauta;
- [x] Integração com API externa que verifique se o CPF do associado está habilitado ou não ao voto.



## ⚙️ Technologies & Tool

- Java 11
- SpringBoot v2.6.0
- MariaDB v10.4.18
- Gradle


## :bookmark_tabs: Instruções

- Inicie a aplicação, por padrão rodará na porta 8080:
```
http://localhost:8080
```

- Para criação de uma pauta, utilize o método POST no seguinte endpoint:
```
http://localhost:8080/api/pauta
```
Exemplo JSON para criação de pauta e sessão:

```
 {
     "titulo": "Nova Pauta",
    "descricao": "Criação de uma nova pauta"
    "tempoDuracao": "23:42:23"
}
```

Obs: a sessão é aberta automaticamente, se o tempo não for passado ela ficará aberta 1 minuto;
- Para computar o voto, o associado deve estar registrado e terá que escolher entre as opções 1 e 2 sendo:
```
1 = Sim 
2 = Não
```

- O cadastro de votos deve seguir o seguinte modelo:
```
{
    "associadoCPF": "434.796.670-90",
    "pautaId": "2",
    "voto": "1"
}
```
- A exibição do resultado da votação é demonstrada via GET através do seguinte endpoint (O número é o id da pauta a ser consultada):
```
http://localhost:8080/api/pauta/1
```

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
