# Compte rendu TP INFO805 : Compilation
## Swan Launay
> Le bon fonctionnement des TP 3 & 4 montre que les TP 1 & 2 fonctionnent également correctement.
## TP 3 & 4 : Exercice 1
### Avancement :
 - Tous les points de l'exercice 1 ont été réalisés.
### Code en entrée du compilateur :
```Ada
let trois = 3;
let neuf = 9;
let vingtSept = (trois * neuf) / (11 mod 10);
output vingtSept
.
```

### Arbre associé :
```
(; (let trois 3) (; (let neuf 9) (; (let vingtEtUn (/ (* trois neuf) (% 11 10))) (output vingtEtUn))))
```

### Code en sortie du compilateur :
```Assembly
CODE SEGMENT
	mov eax, 3
	mov trois, eax
	mov eax, 9
	mov neuf, eax
	mov eax, trois
	push eax
	mov eax, neuf
	pop ebx
	mul eax, ebx
	push eax
	mov eax, 10
	push eax
	mov eax, 11
	pop ebx
	mov ecx, eax
	div ecx, ebx
	mul ecx, ebx
	sub eax, ecx
	pop ebx
	div ebx, eax
	mov eax, ebx
	mov vingtEtUn, eax
	mov eax, vingtEtUn
	out eax
CODE ENDS
```
### Résultat attendu lors de l'exécution du code :

```
>>>>27
>>>>>>>>>>>>>>>>>>>>>> That's all
```
## TP 3 & 4 : Exercice 2

### Avancement :
 - Les points suivant n'ont pas été réalisés :
    - Opérateurs booléens
       - not
       - or
       - and
       - sup
       - sup_eq
       - inf_eq
 - Tout le reste est fonctionnel.

### Code en entrée du compilateur :
```Ada
let a = input;
let b = input;
while (0 < b)
do (let aux=(a mod b); let a=b; let b=aux );
output a
.
```

### Arbre associé :
```
(; (let a input) (; (let b input) (; (while (< 0 b) (do (; (let aux (% a b)) (; (let a b) (let b aux))))) (output a))))
```

### Code en sortie du compilateur :
```Assembly
DATA SEGMENT
	a DD
	b DD
	aux DD
DATA ENDS
CODE SEGMENT
	in eax
	mov a, eax
	in eax
	mov b, eax
debut_while_1:
	mov eax, 0
	push eax
	mov eax, b
	pop ebx
	sub eax, ebx
	jle faux_inf_1
	mov eax, 1
	jmp sortie_inf_1
faux_inf_1:
	mov eax, 0
sortie_inf_1:
	jz sortie_while_1
	mov eax, b
	push eax
	mov eax, a
	pop ebx
	mov ecx, eax
	div ecx, ebx
	mul ecx, ebx
	sub eax, ecx
	mov aux, eax
	mov eax, b
	mov a, eax
	mov eax, aux
	mov b, eax
	jmp debut_while_1
sortie_while_1:
	mov eax, a
	out eax
CODE ENDS
```

### Résultat attendu lors de l'exécution du code :

```
>24 
>36
>>>>12
>>>>>>>>>>>>>>>>>>>>>> That's all
```

