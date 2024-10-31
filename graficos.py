import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from matplotlib.ticker import FuncFormatter

csv = pd.read_csv("dados.csv", encoding='utf-8')

def formatadorInt(x, pos):
    if x >= 1:
        return f'{int(x)}'
    else:
        return f'{x:.1f}'

csv.columns = csv.columns.str.strip().str.lower()

print(csv.columns.tolist())

tempo = 'tempo do sort (ms)'
tamanho = 'tamanho do array'
iteracoes = 'iterações'
trocas = 'trocas'

csv[tempo] = csv[tempo].str.replace('ms', '').str.strip().astype(int)
csv[tamanho] = csv[tamanho].astype(int)
csv[trocas] = pd.to_numeric(csv[trocas], errors='coerce')
csv[iteracoes] = pd.to_numeric(csv[iteracoes], errors='coerce')

if csv.isnull().values.any():
    print("\nRemovendo linhas vazias...")
    csv.dropna(inplace=True)

# calcular média do tempo, iterações e trocas por Tipo de Sort e Tamanho do Array
csv_media = csv.groupby(['tipo de sort', 'tamanho do array']).agg({
    tempo: 'mean',
    iteracoes: 'mean',
    trocas: 'mean'
}).reset_index()

csv_media.rename(columns = {
    tempo: 'Média Tempo (ms)',
    iteracoes: 'Média Iterações',
    trocas: 'Média Trocas'
}, inplace=True)

print("\nDados com médias:")
print(csv_media.head())

sns.set_theme(style="whitegrid")

formatter = FuncFormatter(formatadorInt)

# ========================== MÉDIA DE TEMPO ==========================

plt.figure(figsize=(12, 8))
sns.barplot(
    data = csv_media,
    x = "tamanho do array",
    y = "Média Tempo (ms)",
    hue = "tipo de sort",
    palette = 'muted'
)

plt.title("Média do Tempo", fontsize=16)
plt.xlabel("Tamanho do Array", fontsize=14)
plt.ylabel("Média Tempo (ms)", fontsize=14)
plt.xticks(rotation=45)
plt.legend(title='Tipo de Sort')
plt.yscale('log')
plt.gca().yaxis.set_major_formatter(formatter)

# anotações nas barras
for p in plt.gca().patches:
    height = p.get_height()
    if height > 0:
        plt.annotate(f'{int(height)}',
                        (p.get_x() + p.get_width() / 2., height),
                        ha='center', va='bottom', fontsize=8, color='black',
                        xytext=(0, 5), textcoords='offset points')

plt.tight_layout()
plt.show()

# ========================== MÉDIA DE ITERAÇÕES ==========================

plt.figure(figsize=(12, 8))
sns.barplot(
    data=csv_media,
    x="tamanho do array",
    y="Média Iterações",
    hue="tipo de sort",
    palette='muted'
)

plt.title("Média de Iterações", fontsize=16)
plt.xlabel("Tamanho do Array", fontsize=14)
plt.ylabel("Média Iterações", fontsize=14)
plt.xticks(rotation=45)
plt.legend(title='Tipo de Sort')
plt.yscale('log')
plt.gca().yaxis.set_major_formatter(formatter)

# anotações nas barras
for p in plt.gca().patches:
    height = p.get_height()
    if height > 0:
        plt.annotate(f'{int(height)}',
                        (p.get_x() + p.get_width() / 2., height),
                        ha='center', va='bottom', fontsize=8, color='black',
                        xytext=(0, 5), textcoords='offset points')

plt.tight_layout()
plt.show()

# ========================== MÉDIA DE TROCAS ==========================

plt.figure(figsize=(12, 8))
sns.barplot(
    data=csv_media,
    x="tamanho do array",
    y="Média Trocas",
    hue="tipo de sort",
    palette='muted'

)

plt.title("Média de Trocas", fontsize=16)
plt.xlabel("Tamanho do Array", fontsize=14)
plt.ylabel("Média Trocas", fontsize=14)
plt.xticks(rotation=45)
plt.legend(title='Tipo de Sort')
plt.yscale('log')
plt.gca().yaxis.set_major_formatter(formatter)

# anotações nas barras
for p in plt.gca().patches:
    height = p.get_height()
    if height > 0:
        plt.annotate(f'{int(height)}',
                        (p.get_x() + p.get_width() / 2., height),
                        ha='center', va='bottom', fontsize=8, color='black',
                        xytext=(0, 5), textcoords='offset points')

plt.tight_layout()
plt.show()
