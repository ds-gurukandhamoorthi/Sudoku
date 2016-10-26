/* jshint undef: true, unused: true,  esversion:6, globalstrict:true */
/* globals console */

"use strict";
//methode pour changer une chaine de caracteres en une matrice
//exemple : "__3__41__\n_51__2_3_\n_____1_46\n23_______\n7_5_8_3_2\n_______78\n39_4_____\n_1_9__45_\n__46__9__".asMatrix()
String.prototype.asMatrix=function(){
	const lines = this.split("\n");
	var matrix = [];
	for(let i=0; i < lines.length; i++){
		matrix[i]=[];
		let chars = lines[i].split('');
		for(let j = 0; j<chars.length; j++){
			let value = 0;
			if(/^[1-9]$/.test(chars[j])){
				value = chars[j] * 1;
			}
			matrix[i][j]=value;
		}
	}
	return(matrix);
};


//methode pour changer une chaine de caractere en une matrice de type Cells (voir definition du Cell)
String.prototype.asMatrixOfCells=function(){
	let matrixCells = [];
	const matrix = this.asMatrix();	
	for(let i=0; i<9;i++){
		matrixCells[i]=[]; // chaque ligne est un vecteur. 
		for(let j =0; j<9;j++){
			let val = matrix[i][j];
			matrixCells[i][j]=new Cell({value:val,
								   given : (val !==0 ?true:false)});
		}
	}
	return(matrixCells);
};

//console.log("__3__41__\n_51__2_3_\n_____1_46\n23_______\n7_5_8_3_2\n_______78\n39_4_____\n_1_9__45_\n__46__9__".asMatrixOfCells());//OfCells()));



function Sudoku(str){
	this.carre=str.asMatrixOfCells();
	this.estPossible=function(r,c,val){
		return(this.estPossibleSousCarre(r,c,val) &&
				this.estPossibleHoriz(r,c,val) && 
				this.estPossibleVertic(r,c,val));
	};
}


Sudoku.prototype.estPossibleVertic=function(r, c, val){
	for(let i=0; i<r; i++){
		if(this.carre[i][c].value==val){
			return(false);
		}
	}
	for(let i=r+1; i<9; i++){
		if(this.carre[i][c].given && this.carre[i][c].value==val){
			return(false);
		}
	}
	return true;
};
Sudoku.prototype.estPossibleHoriz=function(r, c, val){
	for(let i=0; i<c; i++){
		if(this.carre[r][i].value==val){
			return(false);
		}
	}
	for(let i=c+1; i<9; i++){
		if(this.carre[r][i].given && this.carre[r][i].value==val){
			return(false);
		}
	}
	return true;
};


Sudoku.prototype.estPossibleSousCarre=function(r, c, val){
	const rOff = Math.floor(r/3)*3; //de combien il faut decaler...
	const cOff = Math.floor(c/3)*3;
	//console.log(rOff,cOff);
	let croise = false ;
	for(let i =rOff; i<rOff+3;i++){
		for(let j=cOff; j<cOff+3; j++){
			if(i == r && j ==c){
				croise = true;
				continue;
			}
			//console.log(i,j,croise);
			//on veut faire un traitement different suivant que le couple(r,c) est crois'e ou non
			if(croise){
				if(this.carre[i][j].given && this.carre[i][j].value==val){
					return(false);
				}
			}else{
				if(this.carre[i][j].value==val){
					return(false);
				}
			}
		}
	}
	return(true);

};

Sudoku.prototype.toMatrix=function(){
	let matrix=[];
	for(let i = 0; i<9; i++){
		matrix[i]=[];
		for(let j = 0; j<9; j++){
			matrix[i][j]= this.carre[i][j].value;
		}
	}
	return(matrix);
};

const div = (n,a) => Math.floor(n/a); //division entiere
const ligne = (n) => div(n,9);
const colonne = (n) => n%9;

Sudoku.prototype.resoudre=function(){
	let backtrackingMode=false;
	let resolu=false;
	let resolvable=true;
	let i=0;
	
	greatloop:
	while( resolvable && !resolu ){
		let r = ligne(i);
		let c = colonne(i);
		console.log(i, r, c);
		console.log(this.toMatrix());
		
		if(backtrackingMode){
			console.log("backtracking...");
			if(i<=0){ //on est en backtracking mode et on atteint la premiere case
				resolvable=false;
				break;
			}
			if(this.carre[r][c].given){ //case avec valeur donnee dans le prob. on va a gauche.
				i--;
				continue;
			}
			if(this.carre[r][c].value==0){// en backtrack mode on recontre une case avec valeur 0. on utilise le fait que case contient 0. Dommage pour l'abstraction.
				i--;
				continue;
			}
			if(this.carre[r][c].value==9){// en backtrack mode on recontre une case avec valeur 9. on a donc essaye toutes les valeurs sur cette case. on va a gauche apres avoir mis 0 dans la case
				this.carre[r][c].value=0;
				i--;
				continue;
			}
			backtrackingMode=false;
			
			
		}else{
			if(i>=81){
				resolu=true;
				break;
			}
			if(this.carre[r][c].given){
				i++;
				continue;
			}
			
			let val=this.carre[r][c].value;
			//let valueChanged=false;
			for(let k=val+1; k<=9;k++){ //utilise le fait que non initialise = 0  ! dommage pour l'abstraction
				
				if(this.estPossible( r,c,k)){
					console.log("possible",r,c,k);
					this.carre[r][c].value=k;
					//valueChanged=true;
					//backtrackingMode=false;
					i++;
					continue greatloop;
				}
				
			
			}
			this.carre[r][c].value=0;
			backtrackingMode=true; //on a essaye toutes les valeurs : aucune fonctionne.
			
			
		}
		
		
	}
	
};

/*

Sudoku.prototype.resoudre=function(){
	//let premiereCase = [0,0];
	//let derniereCase =[8,8];
	//let resolu=false;
	let resolvable=true;
	//var i,j;
	
	var backtrackingMode=false;
	for(let i = 0; i<81 && resolvable;  ){//pour chacune des 81 cases
		let r = ligne(i);
		let c = colonne(i);
		console.log(r,c);
		if(backtrackingMode===true && i<=0){
			resolvable=false;
			break;
		}
		
		if(!this.carre[r][c].given && this.carre[r][c].value==9){
			resolvable=false;
			break;
		}
		
		if(this.carre[r][c].given){
			if(!backtrackingMode){
				i++;
				continue;
			}else{
				i--;
			}
		}

		let val = this.carre[r][c].value;
		
		for(let k=val+1; k<=9;k++){ //utilise le fait que non initialise = 0  ! dommage pour l'abstraction
			if(this.estPossible(r,c,k)){
				console.log(r,c,k);
				this.carre[r][c].value=k;
				backtrackingMode=false;
				i++;
				break;
			}
			console.log(this.toMatrix());
		}
		backtrackingMode=true;
		if(backtrackingMode){
			this.carre[r][c].value=0;
			i--;
			continue;
		}
	
	}
	if(resolvable){
		return(this.carre);
	}else{
		return([]);
	}
	
};

*/

//Cree objet Cell   contient valeur et si la valeur est donnee...
function Cell(params){
	params = params || {};
	this.value=params.value || 0 ;
	this.given=params.given || false;
}
console.log(new Cell({given:false}));

var sudokumoyen1="1____5__4\n_234_6_5_\n_______6_\n9_4____7_\n___567___\n_5____8_3\n_4_______\n_3_7_198_\n2__8____7";
var sudokufacile=   "4_8___93_\n1__24_5__\n97_8__6__\n6_23_____\n_47___21_\n_____64_3\n__3__1_59\n__1_65__2\n_94___1_8";
var sudokufacile2= "___54769_\n_7_9__132\n8______5_\n_523____9\n_31_9_42_\n4____657_\n_1______5\n387__9_6_\n_45612___";
var sudokufacile3= "1___2_9_6\n_25_19_4_\n9_36_5__2\n4__9_7_8_\n_6_8_1__4\n_1__5__3_\n7__3_24_5\n_9_54_26_\n2_4_7___8";
var s=new Sudoku(sudokumoyen1);
console.log(s.toMatrix());
//var s=new Sudoku("4_8_");
console.log(s.estPossibleHoriz(6,4,8),s.estPossibleVertic(6,4,8),s.estPossibleSousCarre(1,4,4));
console.log(s.estPossible(1,4,9));
console.log(s.resoudre());
//console.log(colonne(5));