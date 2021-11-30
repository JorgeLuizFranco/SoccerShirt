//VARIAVEIS****************************************************************
//variaveis onde vamos guardar qual das opções
//de gerenciamento será feita, para facilitar
var acao = "";
var categoria = "";
var json;
var imgSelecionadas = "";
var index = [];

//ANIMAÇÕES COM O SCROLL*************************************************************
// Lógica - https://github.com/origamid/publico/tree/main/animar-ao-scroll-com-javascript-puro/wildbeast-final
// 1 - Selecionar elementos que devem ser animados
// 2 - Definir a classe que é adicionada durante a animação
// 3 - Criar função de animação
// 3.1 - Verificar a distância entre a barra de scroll e o topo do site
// 3.2 - Verificar se a distância do 3.1 + Offset é maior do que a distância entre o elemento e o Topo da Página.
// 3.3 - Se verdadeiro adicionar classe de animação, remover se for falso.
// 4 - Ativar a função de animação toda vez que o usuário utilizar o Scroll
// 5 - Otimizar ativação
// Debounce do Lodash
const debounce = function (func, wait, immediate) {
    let timeout;
    return function (...args) {
        const context = this;
        const later = function () {
            timeout = null;
            if (!immediate) func.apply(context, args);
        };
        const callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func.apply(context, args);
    };
};

var target;
const animationClass = 'animate';

function animeScroll() {
    const windowTop = window.pageYOffset + ((window.innerHeight * 7) / 8);
    target.forEach(function (element) {
        if ((windowTop) > element.offsetTop) {
            element.classList.add(animationClass);
        } else {
            element.classList.remove(animationClass);
        }
    })
}
//BUSCA DE DADOS ESPECIFICOS***************************************************************************************
async function buscaJsonEx(id) {
    var ans = null
    var request = await $.ajax({
        url: "Selecionar" + categoria.charAt(0).toUpperCase() + categoria.slice(1),
        type: 'POST',
        data: id,
        async: true,
        success: function (json) {
            ans = decodeURIComponent(json);
            ans = JSON.parse(ans);
        }
    });
    return ans;
}

//BUSCA DE DADOS GERAIS*****************************************************
async function buscaJson(categ) {
    var ans = null
    var request = await $.ajax({
        url: "Selecionar" + categ.charAt(0).toUpperCase() + categ.slice(1) + "s",
        type: 'POST',
        async: true,
        success: function (json) {
            ans = decodeURIComponent(json);
            ans = JSON.parse(ans);
        }
    });
    return ans;
}

//BUSCA RELAÇÕES*****************************************************
async function buscaRelacao(categ, id) {
    var ans = null
    console.log("id" + id)
    var request = await $.ajax({
        url: "Filtra" + categ.charAt(0).toUpperCase() + categ.slice(1),
        type: 'POST',
        data: id,
        async: true,
        success: function (json) {
            ans = json;
        }
    });
    return ans;
}

//VERIFICA ADMINISTRADOR******************************************************
async function verificaAdm() {
    if ($("#txtUsername").val() == "") {
        $("#txtUsername").focus();
    } else if ($("#txtSenha").val() == "") {
        $("#txtSenha").focus();
    } else {
        //enviamos por ajax o json com os dados para a página que irá verificar os dados
        let dados = { "id": 0, "nome": $("#txtUsername").val(), "paisOrigem": $("#txtSenha").val() };
        var request = await $.ajax({
            url: "VerificaADM",
            type: 'POST',
            data: dados,
            async: true,
            success: function (mensagem) {
                if (mensagem) {
                    $("section").html(`
                    <div class="exibe" id="menu">
                        <h4>Escolha uma categoria</h4>
                        <select id="opcSelect" class="form-control">
                            <option value="notícia">Notícia</option>
                            <option value="time">Time</option>
                            <option value="marca">Marca</option>
                            <option value="liga">Liga</option>
                        </select>
                        <button id="cadastrar" class="btn btn-outline-success btn_categorias" onclick="funcao('cadastrar')"
                            value="cadastrar">Cadastrar</button>
                        <button id="editar" class="btn btn-outline-warning btn_categorias" onclick="funcao('editar')"
                            value="editar">Editar</button>
                        <button id="excluir" class="btn btn-outline-danger btn_categorias" onclick="funcao('excluir')"
                            value="excluir">Excluir</button>
                    </div>
                    <div class="exibe" id="corpo" style="display: none"></div>
                `);
                } else {
                    alert("Usuário ou senha incorreto(s)!");
                }
            }
            //colocar mensagem de erro
        })
    }
}

//EDITAR************************************************************************
//chamada da função para editar
async function editar() {
    let id = $("#opcSelectG option:selected").val();
    let json = await buscaJsonEx(id);
    if (categoria == "noticia") {
        camposNoticia("Editando notícia", id);
        $("#tituloNoticia").val(json.titulo);
        $("#subtituloNoticia").val(json.subtitulo);
        $("#conteudoNoticia").val(json.texto);
        //falta os selects
    } else {
        //verificamos qual é a categoria para enviar os parametros corretos
        if (categoria == "time") {
            camposGerais("Editando time", "do", "Escudo", id);
        } else if (categoria == "marca") {
            camposGerais("Editando marca", "da", "Logo", id);
        } else {
            camposGerais("Editando liga", "da", "Símbolo", id);
            $("#paisLiga").val(json.paisOrigem);
        }
        $("#nome").val(json.nome);
        $("#customFile").val(json.img);
    }
};
//CADASTRAR********************************************************************
//chamada da função para cadastrar
function cadastrar() {
    //vamos direcionar para a função de cadastramento
    //de cada categoria
    if (categoria == "notícia") {
        camposNoticia("Cadastrando uma nova notícia", 0);
    } else if (categoria == "time") {
        camposGerais("Cadastrando um novo time", "do", "Escudo", 0);
    } else if (categoria == "marca") {
        camposGerais("Cadastrando uma nova marca", "da", "Logo", 0);
    } else {
        camposGerais("Cadastrando uma nova liga", "da", "Símbolo", 0);
    }
}
//CAMPOS DE NOTÍCIA******************************************************************
//a função 'camposNoticia()' será chamada no cadastro de notícia ou na edição dela
//terá todos os campos para o usuário inserir sobre a notícia
async function camposNoticia(textH4, textOnclick) {
    $("#corpo").text(``)
    $("#corpo").show()
    $("#corpo").append(`
            <div id="formCorpo">
                    <h4>${textH4}</h4>
                    <div class="form-group">
                        <label class="form-label">Título</label>
                        <label style = "color: red;">*</label>
                        <input type="text" id="tituloNoticia" class="form-control"
                            placeholder="Insira o título da notícia">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Subtítulo</label>
                        <label style = "color: red;">*</label>
                        <textarea id="subtituloNoticia" class="form-control"
                            placeholder="Insira o subtítulo da notícia"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Corpo</label>
                        <label style = "color: red;">*</label>
                        <textarea style="height: 150px;" id="conteudoNoticia" class="form-control"
                            placeholder="Insira o conteúdo da notícia" aria-label="With textarea"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label select-label">Times - Ainda em construção</label>
                        <select id="slcTimes" class="form-control" multiple="multiple" name="ligas[]">
                            <option value="0" disabled>Selecionar time(s)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label select-label">Marcas - Ainda em construção</label>
                        <select id="slcMarcas" class="form-control" multiple="multiple" name="ligas[]">
                            <option value="0" disabled>Selecionar marca(s)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label select-label">Ligas - Ainda em construção</label>
                        <select id="slcLigas" class="form-control" multiple="multiple" name="ligas[]">
                            <option value="0" disabled>Selecionar liga(s)</option>
                        </select>
                    </div>
                    <label class="form-label">Imagem - Ainda em construção</label>
                    <div class="form-group custom-file">
                        <input type="file" class="custom-file-input" id="customFile"
                            accept="image/png, image/jpeg" multiple name="arquivo">
                        <label class="custom-file-label" for="customFile" style="float: left !important;">Insira imagens</label>
                    </div>
                    <img scr="" id="preview">
                    <div class="form-group" id="imgSelecionadas" style="display: none"></div>
                    <div class="form-group">
                        <button type="submit" id="finalizaCadastro" style="margin-top:10px" onclick="enviaNoticia('${textOnclick}')" class="btn btn-outline-light form-control">Finalizar</button>
                    </div>
                </div>`)
    document.getElementById("customFile").addEventListener("change", readImage, false);

    //buscando todos os times
    let json = await buscaJson("time");
    for (let i = 0; i < json.length; i++) {
        $("#slcTimes").append(`
                    <option value="${json[i].id}">${json[i].nome}</option>`);
    }

    //buscando todas as marcas
    json = await buscaJson("marca");
    for (let i = 0; i < json.length; i++) {
        $("#slcMarcas").append(`
                    <option value="${json[i].id}">${json[i].nome}</option>`);
    }

    //buscando todas as ligas
    json = await buscaJson("liga");
    for (let i = 0; i < json.length; i++) {
        $("#slcLigas").append(`
                    <option value="${json[i].id}">${json[i].nome}</option>`);
    }
}
//ENVIA NOTÍCIA********************************************************************
//a função 'enviaNoticia()' vai ser chamada quando for
//finalizar um cadastro de notícia ou finalizar edição de notícia
function enviaNoticia(id) {
    //verifica os campos obrigatórios
    if ($("#tituloNoticia").val() == "") {
        $("#tituloNoticia").focus();
    } else if ($("#subtituloNoticia").val() == "") {
        $("#subtituloNoticia").focus();
    } else if ($("#conteudoNoticia").val() == "") {
        $("#conteudoNoticia").focus();
    } else if ($("#customFile").val() == "") {
        $("#customFile").focus();
    } else {
        //vamos pegar os valores selecionados nos multiples selects
        //se não houver seleção a função irá retornar 0
        let times = getSelectValues(slcTimes);
        let ligas = getSelectValues(slcLigas);
        let marcas = getSelectValues(slcMarcas);

        let noticiaCompleta = { "id": id, "titulo": $("#tituloNoticia").val(), "subtitulo": $("#subtituloNoticia").val(), "conteudo": $("#conteudoNoticia").val(), "times": times, "marcas": marcas, "ligas": ligas, "imagens": imgSelecionadas }

        var request = $.ajax({
            url: "CadastrarNoticia",
            type: 'POST',
            contentType: "application/json",
            data: noticiaCompleta,
            async: true,
            success: function (response) {
                if (id == 0) {
                    alert("Notícia cadastrada com sucesso!!!");
                } else {
                    alert("Notícia editada com sucesso!!!");
                }
                //limpamos os campos
                $("#formCorpo input, textarea, #customFile").val("");
                $("#exibe").text(``);
            }
        })
        console.log(request)
    }
}
//CAMPOS TIME/MARCA/LIGA****************************************************************
function camposGerais(texth4, textCon, txtImg, textOnclick) {
    $("#corpo").text(``)
    $("#corpo").show()
    $("#corpo").append(`
            <div id="formCorpo">
                    <h4>${texth4}</h4>
                    <div class="form-group">
                        <label class="form-label">Nome</label>
                        <label style = "color: red;">*</label>
                        <input type="text" id="nome" class="form-control"
                            placeholder="Insira o nome ${textCon} ${categoria}">
                    </div>
                </div>`);
    // se for liga terá uns campos a mais
    if (categoria == "liga") {
        textCon = "do";
        $("#formCorpo").append(` <div class="form-group">
                        <label class="form-label">País de origem</label>
                        <label style = "color: red;">*</label>
                        <input id="paisLiga" class="form-control"
                            placeholder="Insira o país de origem da liga">
                    </div>`)
    }
    $("#formCorpo").append(`
                    <label class="form-label ">${txtImg}</label>
                    <label style = "color: red;">* - Em construção</label>
                    <div class="form-group custom-file">
                        <input type="file" class="custom-file-input" id="customFile"
                            accept="image/png" name="arquivo">
                        <label class="custom-file-label" for="customFile">Insira uma foto ${textCon} ${txtImg.toLowerCase()}</label>
                    </div>
                    <div class="form-group" id="imgSelecionadas" style="display: none"></div>
                    <div class="form-group">
                        <button type="submit" id="finalizaCadastro" style="margin-top:10px" onclick="enviaGeral(${textOnclick})" class="btn btn-outline-light form-control">Finalizar</button>
                    </div>
                `)
    document.getElementById("customFile").addEventListener("change", readImage, false);
}
//ENVIA TIME/MARCA/LIGA****************************************************************
function enviaGeral(id) {
    //verifica os campos obrigatórios
    if ($("#nome").val() == "") {
        $("#nome").focus();
    } else if ($("#customFile").val() == "") {
        $("#customFile").focus();
    } else {
        //msg é apenas para manter a concordância do genêro
        let msg;
        if (id == 0) {
            msg = " cadastrada com sucesso!!!";
        } else {
            msg = " editada com sucesso!!!";
        }

        let geralCompleto = { "id": id, "nome": $("#nome").val(), "img": $("#customFile").val() };

        //se for liga, como liga tem mais campos, vamos verificar
        if (categoria == "liga") {
            if ($("#paisLiga").val() == "") {
                $("#paisLiga").focus();
            } else {
                geralCompleto = { "id": id, "nome": $("#nome").val(), "paisOrigem": $("#paisLiga").val(), "img": $("#customFile").val() };
            }
        }

        var request = $.ajax({
            url: "Cadastrar" + categoria.charAt(0).toUpperCase() + categoria.slice(1),
            type: 'POST',
            data: geralCompleto,
            async: true,
            success: function (mensagem) {
                if (categoria == "time") {
                    if (msg.charAt(1) == "c") {
                        msg = " cadastrado com sucesso!!!";
                    } else {
                        msg = " editado com sucesso!!!";
                    }
                }
                alert(categoria.charAt(0).toUpperCase() + categoria.slice(1) + msg)
                if (acao == "cadastrar" && acao == "editar") {
                    $("#formCorpo input, textarea, #customFile").val("");
                }
                $("#exibe").text(``);
            }
        })
    }
}
//FUNÇÃO DE DIRECIONA PARA A AÇÃO ESCOLHIDA****************************************************************
//cliques nos botões do menu
async function funcao(param) {
    acao = param;
    //guardamos a categoria selecionada
    categoria = $("#opcSelect option:selected").val();
    //caso a ação selecionada não seja uma cadastrar
    //vamos exibir um select para o administrador selecionar
    //qual das cadastradas vão ser editada ou excluida
    if (acao != "cadastrar") {
        //aqui vamos acessar o futuro json para pegar
        //os dados cadastrados e exibir no select
        //limpa a div onde vamos exibir
        $("#corpo").text(``);
        //mostra ela
        $("#corpo").show();
        $("#corpo").append(`<div id="formCorpo">
                    <h4>Qual ${categoria} deseja ${acao}?</h4>
                    <br>
                    <select id="opcSelectG" class="form-control" required></select>
                    </form>`);
        if (categoria == "notícia") {
            categoria = "noticia";
        }
        $("#formCorpo").append(`<button id="prosseguir" type="submit" class="btn btn-outline-light" style="margin-top:10px" onclick="${acao}('${categoria}')" >Prosseguir</button></div>
                    </form>`);
        let json = await buscaJson(categoria);
        if (json != null) {
            if (categoria == "noticia") {
                for (let i = 0; i < json.length; i++) {
                    $("#formCorpo select").append(`
                        <option value="${json[i].id}">${json[i].id} - ${json[i].titulo}</option>`);
                }
            } else {
                for (let i = 0; i < json.length; i++) {
                    $("#formCorpo select").append(`
                        <option value="${json[i].id}">${json[i].nome}</option>`);
                }
            }
        } else {
            $("#formCorpo select").append(`<option value="0">Não há ${categoria}s cadastrados</option>`);
        }
    } else {
        cadastrar();
    }
}
//pega os valores selecionados dos multiples selects******************************************************************
function getSelectValues(select) {
    var result = "";
    var options = select && select.options;
    var b = false;

    for (var i = 0; i < options.length; i++) {
        if (options[i].selected) {
            b = true;
            if (result == "") {
                result += parseInt(options[i].value);
            } else {
                result += "KQVSUQ" + parseInt(options[i].value);
            }
        }
    }

    if (b == false) {
        result = 0;
    }
    return result;
}
//quando o adm adicionar algumas foto irá adicionar um preview**********************************
async function readImage() {
    imgSelecionadas = "";
    let filenames = [];
    let files = this.files;
    if (files.length > 1) {
        filenames.push(files.length + " imagens selecionadas");
    } else {
        for (let i in files) {
            if (files.hasOwnProperty(i)) {
                filenames.push(files[i].name);
            }
        }
    }
    $(this)
        .next(".custom-file-label")
        .html(filenames.join(","));

    $("#imgSelecionadas").text("");
    $("#imgSelecionadas").show();
    for (let i = 0; i < this.files.length; i++) {
        var file = new FileReader();
        var tam = this.files.length;
        file.onload = function (e) {
            if (i == 0 || i % 2 == 0) {
                $("#imgSelecionadas").append(`<img class="imgNot img-thumbnail float-left" src="${e.target.result}">`)
            } else {
                $("#imgSelecionadas").append(`<img class="imgNot img-thumbnail float-right" src="${e.target.result}">`)
            }

            if (imgSelecionadas == "") {
                imgSelecionadas += e.target.result;
            } else {
                imgSelecionadas += "gabriella2503jorge2505mauricio1106" + e.target.result;
            }
            console.log("o que eu mando: " + e.target.result)

        };
        file.readAsDataURL(this.files[i]);
    }
}
//*************************************************************************************************
function excluir(param) {
    var request = $.ajax({
        url: "Excluir" + param.charAt(0).toUpperCase() + param.slice(1),
        type: 'POST',
        data: $("#opcSelectG option:selected").val(),
        async: true,
        success: function (mensagem) {
            alert(param.charAt(0).toUpperCase() + param.slice(1) + " excluído com sucesso!!!")
            funcao('excluir');
        }
    });

}
//*****************************************************************************************************
function verSenha() {
    $("#olho").mousedown(function () {
        $(".mostraolhinho").attr("type", "text");
    });

    $("#olho").mouseup(function () {
        $(".mostraolhinho").attr("type", "password");
    });

    // para evitar o problema de arrastar a imagem e a senha continuar exposta,
    //citada pelo nosso amigo nos comentários
    $("#olho").mouseout(function () {
        $(".mostraolhinho").attr("type", "password");
    });
}
//exibe todas as notícias no index******************************************************************************************
function exibeNoticias(json) {
    $("section").text("");
    cont = 0;
    seg = 0;
    lista = ['top1', 'top2', 'top3'];
    index = [];

    for (let i = 0; i < json.length; i++) {
        //montar como será exibido as notícias
        index.push(json[i].id);

        let imagens = json[i].imagens.split('gabriella2503jorge2505mauricio1106');

        if (cont % 3 == 0) {
            $("section").append(`
                    <br>
                    <div class="container todosNoticias">
                    <div class="row"></div>
                    </div>`);
        }

        let n = ` "id": ${json[i].id}, "titulo": "${json[i].titulo}", "subtitulo": "${json[i].subtitulo}", "texto": "${json[i].texto}", "imagem": "${json[i].imagens}", "horaC": "${json[i].horaC}", "dataC": "${json[i].dataC}" `;

        $(".container:last-child .row").append(`
                <div class="col-sm maezona" data-anime="${lista[seg]}" onclick="expandeNoticia('${json[i].id}')">
                    <div class="visivel" id="visivel${json[i].id}">
                        <img id="d${i}" src="${imagens[0]}">
                        <h2>${json[i].titulo}</h2>
                    </div>
                    <div class="invisivel">
                        <h5>${json[i].subtitulo}</h5>
                    </div>
                </div>`);
        cont++;
        seg++;
        if (seg == 3) {
            seg = 0;
        }
    }
    target = document.querySelectorAll('[data-anime]');
    if (target.length) {
        window.addEventListener('scroll', debounce(function () {
            animeScroll();
        }, 200));
    }
}
//exibe na tela do index*************************************************************************************************
async function completaIndex() {
    $(".containerIMG").text("");
    $(".containerIMG").html(`<h1>SoccerShirt</h1>`);
    //pegamos os times cadastrados e colocamos no navbar**************************************************************************************
    var json = await buscaJson("time");
    //json = [{ "id": 1, "nome": 'time 1' }, { "id": 2, "nome": 'time 2' }, { "id": 3, "nome": 'time 3' }, { "id": 4, "nome": 'time 4' }, { "id": 5, "nome": 'time 5' }, { "id": 1, "nome": 'time 1' }, { "id": 2, "nome": 'time 2' }, { "id": 3, "nome": 'time 3' }, { "id": 4, "nome": 'time 4' }, { "id": 5, "nome": 'time 5' }];
    for (let i = 0; i < json.length; i++) {
        $("#dropTimes").append(`
        <li class="dropdown-item" onclick="funcaoDrop('${json[i].id}', 'time', '${json[i].nome}')">${json[i].nome}</li>`);
    }
    //pegamos as marcas cadastrados e colocamos no navbar**************************************************************************************
    json = await buscaJson("marca");
    //json = [{ "id": 1, "nome": 'marca 1' }, { "id": 2, "nome": 'marca 2' }, { "id": 3, "nome": 'marca 3' }, { "id": 4, "nome": 'marca 4' }, { "id": 5, "nome": 'marca 5' }];
    for (let i = 0; i < json.length; i++) {
        $("#dropMarcas").append(`
        <li class="dropdown-item" onclick="funcaoDrop('${json[i].id}', 'marca', '${json[i].nome}')">${json[i].nome}</li>`);
    }
    //pegamos as marcas cadastrados e colocamos no navbar**************************************************************************************
    json = await buscaJson("liga");
    //json = [{ "id": 1, "nome": 'liga 1' }, { "id": 2, "nome": 'liga 2' }, { "id": 3, "nome": 'liga 3' }, { "id": 4, "nome": 'liga 4' }, { "id": 5, "nome": 'liga 5' }];
    for (let i = 0; i < json.length; i++) {
        $("#dropLigas").append(`
        <li class="dropdown-item" onclick="funcaoDrop('${json[i].id}', 'liga', '${json[i].nome}')">${json[i].nome}</li>`);
    }
    //chamamos a função que irá exibir as notícias e passamos como parametro um json com todas as notícias a serem exibidas
    json = await buscaJson("noticia");
    exibeNoticias(json);
    //exibeNoticias([{ "id": 1, "titulo": 'Corinthians relembra visitas e parabeniza Mauricio de Sousa', "subtitulo": "Cartunista criador da Turma da Mônica completa 86 anos nesta quarta-feira", "texto": "O Corinthians usou as redes sociais para parabenizar nesta quarta-feira o cartunista e empresário Mauricio de Sousa, criador da Turma da Mônica. Mauricio de Sousa, que já fez algumas visitas à sede e ao CT do Corinthians, completa 86 anos nesta quarta. Cascão, um dos personagens de maior sucesso do cartunista, é torcedor do Timão. Ele já apareceu em diferentes situações com a camisa do clube, inclusive após a conquista do título Mundial de 2012.", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 2, "titulo": 'titulo 2 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 3, "titulo": 'titulo 3 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 4, "titulo": 'titulo 4 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 5, "titulo": 'titulo 5 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 6, "titulo": 'titulo 6 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 7, "titulo": 'titulo 7 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 8, "titulo": 'titulo 8 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 9, "titulo": 'titulo 9 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 10, "titulo": 'titulo 10 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 11, "titulo": 'titulo 11 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }])
    animeScroll();
}
//abre a notícia clicada*****************************************************************************************************************
async function expandeNoticia(id) {
    //scroll normal
    $('html,body').scrollTop(0);
    $('header').removeClass('remove').addClass('animate');
    $('section').removeClass('remove').addClass('animate');
    $('.navbar').css("position", "relative");

    //vamos setar uma categoria para usar na função de busca
    categoria = "noticia";
    //json = { "id": 2, "titulo": 'titulo 2 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" };
    let json = await buscaJsonEx(id);
    let imagens = json.imagens.split('gabriella2503jorge2505mauricio1106');

    $("section").html(`<div class="container" style="margin: auto!important;">
            <br>
            <div class="row">
                <div class="col-sm">
                    <h1>${json.titulo}</h1>
                </div>
            </div>
            <div class="row">
              <div class="col-sm">
                <h5>${json.subtitulo}</h5>
              </div>
            </div>
            <div class="row" style="color: #495057;font-size: small;">
                <div class="col-sm">
                    <hr>
                        <span>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-clock" viewBox="0 0 16 16">
                                <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                            </svg>
                            <strong>${json.data}</strong>
                            ${json.hora}
                        </span>
                        <span style="float: right;">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-clock" viewBox="0 0 16 16">
                                <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                            </svg>
                            <strong>${json.data}</strong>
                            ${json.hora}
                        </span>
                    <hr>
                </div>
            </div>
            <div class="row">
                <div class="col-sm divCarrossel">
                    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="height: 100%; width: 100%;">
                        <ol class="carousel-indicators"></ol>
                        <div class="carousel-inner"></div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        </a>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
              <div class="col-sm">
                <p class="text-xl-left">${json.texto}</p>
              </div>
            </div>
          </div>`);

    for (let i = 0; i < imagens.length; i++) {
        if (i == 0) {
            $(".carousel-indicators").append(`<li data-target="#carouselExampleIndicators" data-slide-to="${i}" class="active"></li>`)
            $(".carousel-inner").append(`<div class="carousel-item active">
                <img class="d-block img-fluid" src="${imagens[i]}">
              </div>`);
        } else {
            $(".carousel-indicators").append(`<li data-target="#carouselExampleIndicators" data-slide-to="${i}"></li>`)
            $(".carousel-inner").append(`<div class="carousel-item">
                <img class="d-block img-fluid" src="${imagens[i]}">
              </div>`);
        }
    }

}
//FUNÇÃO DE PESQUISA***************************************************************************************************************
async function buscar() {
    var string = $("#inputPesquisa").val();

    if (string.trim() == "" || string == "") {
        $("#inputPesquisa").focus();
    } else {
        //voltamos tudo a posição normal, caso o usuário faça uma pesquisa com uma notícia expandida
        $('section').removeClass('animate').addClass('remove');
        $('header').removeClass('animate').addClass('remove');
        $('.navbar').css("position", "fixed");

        noticias = await buscaJson("noticia");
        // noticias = [{ "id": 1, "titulo": 'Corinthians relembra visitas e parabeniza Mauricio de Sousa', "subtitulo": "Cartunista criador da Turma da Mônica completa 86 anos nesta quarta-feira", "texto": "O Corinthians usou as redes sociais para parabenizar nesta quarta-feira o cartunista e empresário Mauricio de Sousa, criador da Turma da Mônica. Mauricio de Sousa, que já fez algumas visitas à sede e ao CT do Corinthians, completa 86 anos nesta quarta. Cascão, um dos personagens de maior sucesso do cartunista, é torcedor do Timão. Ele já apareceu em diferentes situações com a camisa do clube, inclusive após a conquista do título Mundial de 2012.", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 2, "titulo": 'titulo 2 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 3, "titulo": 'titulo 3 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 4, "titulo": 'titulo 4 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 5, "titulo": 'titulo 5 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 6, "titulo": 'titulo 6 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 7, "titulo": 'titulo 7 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 8, "titulo": 'titulo 8 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 9, "titulo": 'titulo 9 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 10, "titulo": 'titulo 10 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }, { "id": 11, "titulo": 'titulo 11 da notícia', "subtitulo": "Subtitulo da notícia", "texto": "texto da notícia", "hora": "22:37", "data": "27/10/2021", "imagem": "caminho da imagem" }];
        var noticiasPesquisadas = [];
        for (let noticia of noticias) {
            if (noticia.titulo.toUpperCase().includes(string.toUpperCase()) || noticia.subtitulo.toUpperCase().includes(string.toUpperCase()) || noticia.texto.toUpperCase().includes(string.toUpperCase())) {
                noticiasPesquisadas.push({ "id": noticia.id, "titulo": noticia.titulo, "subtitulo": noticia.subtitulo, "texto": noticia.texto, "imagem": noticia.imagens, "horaC": noticia.horaC, "dataC": noticia.dataC });
            }
        }
        if (noticiasPesquisadas.length > 0) {
            $(".containerIMG").text("");
            $(".containerIMG").html(`<div class="row"><h1>Resultados para a pesquisa "${string}":</h1></div>`);
            exibeNoticias(noticiasPesquisadas);
        } else {
            $(".containerIMG").text("");
            $("section").text("");
            $(".containerIMG").html(`
            <div class="container" style="text-align: center">
                <div class="row">
                    <div class="col-ms"><h1>Não encontramos resultados para pesquisa:</h1></div>
                </div>
                <div class="row">
                    <div class="col-ms"><h3>${string}<h3></div>
                </div>
                <div class="row">
                    <div class="col-ms"><p>Tente pesquisar outra palavra</p></div></div>`);
        }
    }
}
//FUNÇÃO DE FILTRAGEM******************************************************************************************************
async function funcaoDrop(id, param, nome) {
    //voltamos tudo a posição normal, caso o usuário faça uma filtragem com uma notícia expandida
    $('section').removeClass('animate').addClass('remove');
    $('header').removeClass('animate').addClass('remove');
    $('.navbar').css("position", "fixed");

    //chamamos a função que irá retornar os ids
    //das notícias relacionadas o que for selecionado
    let idsNot = await buscaRelacao(param, id);

    if (idsNot == null || idsNot == []) {
        $("section").text("");
        $(".containerIMG").text("");
        $(".containerIMG").html(`<h1>Não há notícias relacionadas com "${nome}".</h1>`);

    } else {
        $(".containerIMG").text("");
        $(".containerIMG").html(`<h1>Exibindo notícias relacionadas com "${nome}":</h1>`);
        noticiasPesquisadas = [];
        json = await buscaJson('noticia');
        for (let i = 0; i < json.length; i++) {
            for (let j = 0; j < idsNot.length; j++) {
                if (json[i].id == idsNot[j]) {
                    noticiasPesquisadas.push({ "id": json[i].id, "titulo": json[i].titulo, "subtitulo": json[i].subtitulo, "texto": json[i].texto, "imagem": json[i].imagens, "horaC": json[i].horaC, "dataC": json[i].dataC });
                    break;
                }
            }
        }
        exibeNoticias(noticiasPesquisadas);
    }
}
