#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_NAME_LEN 100
#define MAX_HOUSE_LEN 50
#define MAX_SPECIES_LEN 50
#define MAX_PATRONUS_LEN 50
#define MAX_ACTOR_NAME_LEN 100
#define MAX_EYE_COLOUR_LEN 30
#define MAX_GENDER_LEN 10
#define MAX_BOOLEAN_RESPONSE_LEN 10
#define MAX_HAIR_COLOUR_LEN 30
#define MAX_ALTERNATE_NAMES_LEN 200
#define MAX_DATE_LEN 11
#define MAX_LEN 37
#define NUM_CHARS 256
#define NUM_UUIDS 405

typedef struct
{
    char id[37]; // UUID string
    char name[MAX_NAME_LEN];
    char alternateNames[MAX_ALTERNATE_NAMES_LEN];
    char house[MAX_HOUSE_LEN];
    char ancestry[MAX_NAME_LEN];
    char species[MAX_SPECIES_LEN];
    char patronus[MAX_PATRONUS_LEN];
    char hogwartsStaff[MAX_BOOLEAN_RESPONSE_LEN];
    char hogwartsStudent[MAX_BOOLEAN_RESPONSE_LEN];
    char actorName[MAX_ACTOR_NAME_LEN];
    char alive[MAX_BOOLEAN_RESPONSE_LEN];
    char dateOfBirth[MAX_DATE_LEN];
    int yearOfBirth;
    char eyeColour[MAX_EYE_COLOUR_LEN];
    char gender[MAX_GENDER_LEN];
    char hairColour[MAX_HAIR_COLOUR_LEN];
    char wizard[MAX_BOOLEAN_RESPONSE_LEN];
} Personagem;

int comparisons = 0;
int swaps = 0;

void assignField(char *field, const char *value, size_t max_len)
{
    if (strlen(value) < max_len)
    {
        strcpy(field, value);
    }
    else
    {
        strncpy(field, value, max_len);
        field[max_len - 1] = '\0';
    }
}

void cleanAlternateNames(char *src)
{
    char *dst = src;
    while (*src)
    {
        if (*src != '[' && *src != ']' && *src != '\'' && *src != '\"')
        {
            *dst++ = *src;
        }
        src++;
    }
    *dst = '\0';
}

char* my_strsep(char **stringp, const char *delim)
{
    if (*stringp == NULL)
    {
        return NULL;
    }

    char *start = *stringp;
    char *p;

    p = (delim[0] == '\0') ? NULL : strpbrk(start, delim);
    if (p == NULL)
    {
        *stringp = NULL;
    }
    else
    {
        *p = '\0';
        *stringp = p + 1;
    }

    return start;
}

void lerCsv(const char* path, Personagem** personagens, int* count)
{
    FILE* file = fopen(path, "r");
    if (!file)
    {
        printf("File could not be opened.\n");
        return;
    }
    *personagens = malloc(sizeof(Personagem) * 405);

    *count = 0;
    char line[1024];
    char *rest;

    fgets(line, sizeof(line), file);
    while (fgets(line, sizeof(line), file))
    {
        Personagem p = {0};
        char *token;
        int index = 0;
        rest = line;


        while ((token = my_strsep(&rest, ";")) && index < 18)
        {
            if (token == NULL || strlen(token) == 0)
            {
                token = "";
            }

            switch (index)
            {
            case 0:
                assignField(p.id, token, sizeof(p.id));
                break;
            case 1:
                assignField(p.name, token, sizeof(p.name));
                break;
            case 2:
                assignField(p.alternateNames, token, sizeof(p.alternateNames));
                break;
            case 3:
                assignField(p.house, token, sizeof(p.house));
                break;
            case 4:
                assignField(p.ancestry, token, sizeof(p.ancestry));
                break;
            case 5:
                assignField(p.species, token, sizeof(p.species));
                break;
            case 6:
                assignField(p.patronus, token, sizeof(p.patronus));
                break;
            case 7:
                assignField(p.hogwartsStaff, (strcmp(token, "VERDADEIRO") == 0) ? "true" : "false", sizeof(p.hogwartsStaff));
                break;
            case 8:
                assignField(p.hogwartsStudent, (strcmp(token, "VERDADEIRO") == 0) ? "true" : "false", sizeof(p.hogwartsStudent));
                break;
            case 9:
                assignField(p.actorName, token, sizeof(p.actorName));
                break;
            case 10:
                assignField(p.alive, (strcmp(token, "VERDADEIRO") == 0) ? "true" : "false", sizeof(p.alive));
                break;
            case 11:
                break;
            case 12:
                assignField(p.dateOfBirth, token, sizeof(p.dateOfBirth));
                break;
            case 13:
                p.yearOfBirth = atoi(token);
                break;
            case 14:
                assignField(p.eyeColour, token, sizeof(p.eyeColour));
                break;
            case 15:
                assignField(p.gender, token, sizeof(p.gender));
                break;
            case 16:
                assignField(p.hairColour, token, sizeof(p.hairColour));
                break;
            case 17:
                assignField(p.wizard, (strcmp(token, "VERDADEIRO") == 0) ? "true" : "false", sizeof(p.wizard));
                break;
            }
            index++;
        }
        cleanAlternateNames(p.alternateNames);
        (*personagens)[*count] = p;
        (*count)++;
    }

    fclose(file);
}

Personagem* getPersonagemById(Personagem* personagens, int count, const char* id)
{
    for (int i = 0; i < count; i++)
    {
        if (strcmp(personagens[i].id, id) == 0)
        {
            return &personagens[i];
        }
    }
    return NULL;
}

void printPersonagem(Personagem* p)
{
    if (p == NULL)
    {
        printf("Personagem not found.\n");
        return;
    }

    char dateOfBirthFormatted[MAX_DATE_LEN] = "";
    if (strcmp(p->dateOfBirth, "") != 0)
    {
        int day, month, year;
        sscanf(p->dateOfBirth, "%d-%d-%d", &day, &month, &year);
        sprintf(dateOfBirthFormatted, "%02d-%02d-%d", day, month, year);
    }
    printf("[%s ## %s ## {%s} ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ## %s]\n",
           p->id,
           p->name,
           strlen(p->alternateNames) > 0 ? p->alternateNames : "",
           p->house,
           p->ancestry,
           p->species,
           p->patronus[0] ? p->patronus : "",
           p->hogwartsStaff,
           p->hogwartsStudent,
           p->actorName[0] ? p->actorName : "",
           p->alive ? "true" : "false",
           p->dateOfBirth,
           p->yearOfBirth,
           p->eyeColour,
           p->gender,
           p->hairColour,
           p->wizard ? "true" : "false");
}


void swapPersonagem(Personagem *p1, Personagem *p2)
{
    Personagem temp = *p1;
    *p1 = *p2;
    *p2 = temp;
}

void printSortedPersonagens(Personagem *personagens, int count)
{
    for (int i = 0; i < count; i++)
    {
        printPersonagem(&personagens[i]);
        //printf("qtd impressa: %d\n", count);

    }
}

int getDigit(const char *id, int pos)
{
    return id[pos];
}

void quicksortPersonagem(Personagem *personagens, int esq, int dir)
{
    if (esq < dir)
    {
        int i = esq, j = dir;
        Personagem pivot = personagens[(esq + dir) / 2];

        while (i <= j)
        {
            while (strcmp(personagens[i].house, pivot.house) < 0 ||
                    (strcmp(personagens[i].house, pivot.house) == 0 && strcmp(personagens[i].name, pivot.name) < 0))
            {
                comparisons++;
                i++;
            }
            while (strcmp(personagens[j].house, pivot.house) > 0 ||
                    (strcmp(personagens[j].house, pivot.house) == 0 && strcmp(personagens[j].name, pivot.name) > 0))
            {
                comparisons++;
                j--;
            }

            if (i <= j)
            {
                Personagem temp = personagens[i];
                personagens[i] = personagens[j];
                personagens[j] = temp;
                swaps++;

                i++;
                j--;
            }
        }

        if (esq < j) quicksortPersonagem(personagens, esq, j);
        if (i < dir) quicksortPersonagem(personagens, i, dir);
    }
}

void selectionSortRecursive(Personagem *personagens, int n, int index)
{
    if (index == n - 1)
    {
        return;
    }

    int menor = index;
    for (int i = index + 1; i < n; i++)
    {
        comparisons++;
        if (strcmp(personagens[i].name, personagens[menor].name) < 0)
        {
            menor = i;
        }
    }

    if (menor != index)
    {
        swapPersonagem(&personagens[index], &personagens[menor]);
        swaps++;
    }

    selectionSortRecursive(personagens, n, index + 1);
}

void shellSort(Personagem *personagens, int n)
{
    int i, j, k;
    Personagem temp;

    int gaps[] = {701, 301, 132, 57, 23, 10, 4, 1}; // Sequência de Sedgewick
    int totalGaps = sizeof(gaps) / sizeof(gaps[0]);

    for(k = 0; k < totalGaps; k++)
    {
        int gap = gaps[k];
        for(i = gap; i < n; i++)
        {
            temp = personagens[i];
            j = i;

            while (j >= gap && (strcmp(personagens[j - gap].eyeColour, temp.eyeColour) > 0 ||
                                (strcmp(personagens[j - gap].eyeColour, temp.eyeColour) == 0 &&
                                 strcmp(personagens[j - gap].name, temp.name) > 0)))
            {
                personagens[j] = personagens[j - gap];
                j -= gap;
                swaps++;
                comparisons++;
            }
            if (j != i)
            {
                personagens[j] = temp;
                swaps++;
            }
        }
    }

}

void bubbleSort(Personagem *personagens, int n)
{
    bool swapped;
    for(int i = 0; i < n - 1; i++)
    {
        swapped = false;
        for(int j = 0; j < n - 1; j++)
        {
            comparisons++;
            if (strcmp(personagens[j].hairColour, personagens[j + 1].hairColour) > 0 ||
                    (strcmp(personagens[j].hairColour, personagens[j + 1].hairColour) == 0 &&
                     strcmp(personagens[j].name, personagens[j + 1].name) > 0))
            {
                swapPersonagem(&personagens[j], &personagens[j+1]);
                swaps++;
                swapped = true;
            }
        }

        if(!swapped) break;
    }
}

void radixSort(Personagem *personagens, int n, int tam)
{
    Personagem *temp = (Personagem *)malloc(n * sizeof(Personagem));
    if (temp == NULL)
    {
        printf("Erro ao alocar memória.\n");
        return;
    }

    for (int pos = tam - 1; pos >= 0; pos--)
    {
        int count[NUM_CHARS] = {0};

        for (int i = 0; i < n; i++)
        {
            int digit = getDigit(personagens[i].id, pos);
            count[digit]++;
            comparisons++;
        }

        for (int i = 1; i < NUM_CHARS; i++)
        {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--)
        {
            int digit = getDigit(personagens[i].id, pos);
            temp[--count[digit]] = personagens[i];
            swaps++;
        }

        for (int i = 0; i < n; i++)
        {
            personagens[i] = temp[i];
        }
    }

    free(temp);
}

void readInputs(Personagem *personagens, int totalPersonagensCount, Personagem **selectedPersonagens, int *selectedCount)
{
    char inputID[37];
    scanf("%36s", inputID);

    while (strcmp(inputID, "FIM") != 0)
    {
        Personagem* p = getPersonagemById(personagens, totalPersonagensCount, inputID);
        if (p != NULL)
        {
            (*selectedPersonagens)[(*selectedCount)++] = *p;
        }
        scanf("%36s", inputID);
    }
}

void arraySortAndLog(Personagem *personagens, int personagensCount)
{
    clock_t start = clock();
    radixSort(personagens, personagensCount, MAX_LEN - 1);
    clock_t end = clock();

    double time_spent = (double)(end - start) / CLOCKS_PER_SEC * 1000;
    FILE *logFile = fopen("829255_radixsort.txt", "w");
    if (logFile != NULL)
    {
        fprintf(logFile, "829255\t%d\t%d\t%f\n", comparisons, swaps, time_spent);
        fclose(logFile);
    }

}

Personagem* initializeData(char* path, int* count)
{
    Personagem *personagens;
    lerCsv(path, &personagens, count);
    return personagens;
}

void clear(Personagem *allPersonagens, Personagem *selectedPersonagens)
{
    free(allPersonagens);
    free(selectedPersonagens);
}

int main()
{
    char* caminhoVerde = "/tmp/characters.csv";
    char* caminhoPC = "C:/Users/joaod/Downloads/characters.csv";

    int totalPersonagensCount = 0;
    Personagem* allPersonagens = initializeData(caminhoVerde, &totalPersonagensCount);
    Personagem* selectedPersonagens = malloc(sizeof(Personagem) * totalPersonagensCount);
    int selectedCount = 0;

    readInputs(allPersonagens, totalPersonagensCount, &selectedPersonagens, &selectedCount);
    arraySortAndLog(selectedPersonagens, selectedCount);

    printSortedPersonagens(selectedPersonagens, selectedCount);

    clear(allPersonagens, selectedPersonagens);
    return 0;
}
