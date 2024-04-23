#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

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

    fgets(line, sizeof(line), file);
    while (fgets(line, sizeof(line), file))
    {
        Personagem p = {0};
        char *token;
        int index = 0;

        token = strtok(line, ";");
        while (index < 18)
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
            token = (index < 17) ? strtok(NULL, ";") : NULL;
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
        sscanf(p->dateOfBirth, "%d/%d/%d", &day, &month, &year);
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


void quicksortPersonagem(Personagem *personagens, int esq, int dir) {
    if (esq < dir) {
        int i = esq, j = dir;
        Personagem pivot = personagens[(esq + dir) / 2];

        while (i <= j) {
            while (strcmp(personagens[i].name, pivot.name) < 0) i++;
            while (strcmp(personagens[j].name, pivot.name) > 0) j--;

            if (i <= j) {
                Personagem temp = personagens[i];
                personagens[i] = personagens[j];
                personagens[j] = temp;

                i++;
                j--;
            }
        }

        if (esq < j) quicksortPersonagem(personagens, esq, j);
        if (i < dir) quicksortPersonagem(personagens, i, dir);
    }
}

void swapPersonagem(Personagem *p1, Personagem *p2)
{
    Personagem temp = *p1;
    *p1 = *p2;
    *p2 = temp;
}

void printSortedPersonagens(Personagem *personagens, int count) {
    for (int i = 0; i < count; i++) {
        printPersonagem(&personagens[i]);
    }
}

int main() {
    Personagem* allPersonagens = NULL;
    int totalPersonagensCount = 0;

    lerCsv("/tmp/characters.csv", &allPersonagens, &totalPersonagensCount);

    Personagem* selectedPersonagens = malloc(sizeof(Personagem) * totalPersonagensCount);
    int selectedCount = 0;

    char inputID[37];
    scanf("%36s", inputID);

    while (strcmp(inputID, "FIM") != 0) {
        Personagem* p = getPersonagemById(allPersonagens, totalPersonagensCount, inputID);
        if (p != NULL) {
            selectedPersonagens[selectedCount++] = *p;
        }

        scanf("%36s", inputID);
    }

    if (selectedCount > 0) {
        quicksortPersonagem(selectedPersonagens, 0, selectedCount - 1);
        printSortedPersonagens(selectedPersonagens, selectedCount);
    }

    free(allPersonagens);
    free(selectedPersonagens);
    return 0;
}

