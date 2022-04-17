DATA SEGMENT
	trois DD
	neuf DD
	vingtSept DD
DATA ENDS
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
	mov vingtSept, eax
	mov eax, vingtSept
	out eax
CODE ENDS

