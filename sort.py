import subprocess

tamanhos = ["1000", "10000", "100000", "500000", "1000000"]
tipos = ["quick", "bogo", "merge", "gnome"]

for i in range(len(tipos)): # passa pelos tipos
    seed = 123 # reseta a seed quando muda o tipo de sort
    for j in range(len(tamanhos)):
        seed = 123 # reseta a seed quando troca o tamanho da array
        for k in range(10): # repete 10 vezes cada tamanho alterando a seed
            compileCommand = ["javac", "Sort.java"]
            argumentos = [tipos[i], tamanhos[j] , f"{seed}"] # tipo, tamanho, seed
            print(tipos[i], tamanhos[j] , f"{seed}")
            runCommand = ["java", "Sort"] + argumentos
            seed += 1

            runProcess = subprocess.run(compileCommand, capture_output=True, text=True)
            runProcess = subprocess.run(runCommand, capture_output=True, text=True)

            if runProcess.returncode != 0:
                print(runProcess)
                print("Erro na compilação:")
                print(runProcess.stderr)
            else:
                print("Compilação bem-sucedida.")
                print(runProcess.stdout)