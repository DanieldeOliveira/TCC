function inserirElemento(elemento){

     if(elemento.tipoMidia == 'img'){

        console.log("Inserindo a imagem");
        elementoAInserir = document.createElement("img");
        elementoAInserir.id = elemento.id;
        elementoAInserir.src = elemento.url;
        elementoAInserir.style.display = 'block';
        elementoAInserir.style.position = 'absolute';
        elementoAInserir.style.width = elemento.widthImagem +'px';
        elementoAInserir.style.height = elemento.heightImagem +'px';
        elementoAInserir.style.left = elemento.posicao.x+'px';
        elementoAInserir.style.top = elemento.posicao.y+'px';
        console.log("valor da imagem = " + elementoAInserir.toString() );
         $('.elementosInteracao').append(elementoAInserir);
       //$('#elementosInteracao').html(elementoAInserir);

        /*var imagem = document.querySelector('#teste');
        imagem.src = elemento.url;
        imagem.style.top = elemento.posicao.y+'px';
        imagem.style.left = elemento.posicao.x+'px';
        imagem.style.width = elemento.widthImagem +'px';
        imagem.style.height =  elemento.heightImagem +'px';*/



    }
    else if(elemento.tipoMidia == 'comentario'){

        elementoAInserir = document.createElement("textarea");
        elementoAInserir.id = elemento.id;
        elementoAInserir.textContent = elemento.texto;
        elementoAInserir.style.backgroundColor = '#FFD700';
        elementoAInserir.style.fontFamily = 'Coronetscript, cursive';
        elementoAInserir.style.fontSize = '150%';
        elementoAInserir.style.display = 'block';
        elementoAInserir.style.position = 'absolute';
         elementoAInserir.style.width = elemento.widthImagem +'px';
         elementoAInserir.style.height = elemento.heightImagem +'px';
        elementoAInserir.style.left = elemento.posicao.x +'px';
        elementoAInserir.style.top = elemento.posicao.y+'px';
         $('.elementosInteracao').append(elementoAInserir);


    }

}


function editarElemento(elemento){

    var novoelemento = JSON.parse(elemento);
    var elementoOrig = document.getElementById(novoelemento.id);

    elementoOrig.left = novoelemento.left;
    elementoOrig.top = novoelemento.top;
    elementoOrig.load();

}

function esconderElemento(elemento){
    console.log("esconderElemento = " + elemento);
    document.getElementById(elemento).style.display = 'none';
}

function mostrarCriarAplicacao(){


    document.getElementById('interacao').style.display = 'block';

}

function adicionarImagem(elemento){

    var dados = JSON.parse(elemento);
    Console.log('adicionarImagem com src = ' + dados.url);
    //document.getElementById('img').src = dados.src;

}

function limparTela(){

    $('.elementosInteracao').html('');
}



function testeFuncao(elemento){

    /*var imagem = document.querySelector('#teste');
    imagem.src = elemento.url;
    imagem.style.top = elemento.posicao.y+'px';
    imagem.style.left = elemento.posicao.x+'px';*/

    console.log("chamada para a função testeFuncao");
    //console.log("elemento imagem  = " + document.getElementById('teste'));
    //document.getElementById("teste").src = elemento.url;


}