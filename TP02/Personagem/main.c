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
#define MAX_HAIR_COLOUR_LEN 30
#define MAX_ALTERNATE_NAMES_LEN 200
#define MAX_DATE_LEN 11

typedef struct {
    char id[37]; // UUID string
    char name[MAX_NAME_LEN];
    char alternateNames[MAX_ALTERNATE_NAMES_LEN];
    char house[MAX_HOUSE_LEN];
    char ancestry[MAX_NAME_LEN];
    char species[MAX_SPECIES_LEN];
    char patronus[MAX_PATRONUS_LEN];
    bool hogwartsStaff;
    bool hogwartsStudent;
    char actorName[MAX_ACTOR_NAME_LEN];
    bool alive;
    char dateOfBirth[MAX_DATE_LEN];
    int yearOfBirth;
    char eyeColour[MAX_EYE_COLOUR_LEN];
    char gender[MAX_GENDER_LEN];
    char hairColour[MAX_HAIR_COLOUR_LEN];
    bool wizard;
} Personagem;

void cleanAlternateNames(char *src) {
    char *dst = src;
    while (*src) {
        if (*src != '[' && *src != ']' && *src != '\'' && *src != '\"') {
            *dst++ = *src;
        }
        src++;
    }
    *dst = '\0';
}

void lerCsv(const char* path, Personagem** personagens, int* count) {
    FILE* file = fopen(path, "r");
    if (!file) {
        printf("File could not be opened.\n");
        return;
    }
    *personagens = malloc(sizeof(Personagem) * 405);

    char line[1024];
    fscanf(file, "%*[^\n]\n");

    while (!feof(file)) {
        Personagem p = {0};
        if (fscanf(file, "%36[^;];%99[^;];%199[^;];%49[^;];%99[^;];%49[^;];%49[^;];%d;%d;%99[^;];%d;%10[^;];%d;%29[^;];%9[^;];%29[^;];%d\n",
                       p.id, p.name, p.alternateNames, p.house, p.ancestry, p.species, p.patronus,
                       &p.hogwartsStaff, &p.hogwartsStudent, p.actorName, &p.alive, p.dateOfBirth,
                       &p.yearOfBirth, p.eyeColour, p.gender, p.hairColour, &p.wizard) == 17) {
            cleanAlternateNames(p.alternateNames);
            (*personagens)[*count] = p;
            (*count)++;
        } else {
            printf("Failed to read a line or incomplete data.\n");
            break;
        }
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

void printPersonagem(Personagem* p) {
    if (p == NULL) {
        printf("Personagem not found.\n");
        return;
    }

    char dateOfBirthFormatted[MAX_DATE_LEN] = "";
    if (strcmp(p->dateOfBirth, "") != 0) {
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
           p->hogwartsStaff ? "true" : "false",
           p->hogwartsStudent ? "true" : "false",
           p->actorName[0] ? p->actorName : "N/A",
           p->alive ? "true" : "false",
           dateOfBirthFormatted,
           p->yearOfBirth,
           p->eyeColour,
           p->gender,
           p->hairColour,
           p->wizard ? "true" : "false");
}

int main() {
    Personagem* personagens = NULL;
    int count = 0;

    lerCsv("C:/Users/joaod/Downloads/characters.csv", &personagens, &count);

    char inputID[37];
    printf("Enter ID (or 'FIM' to exit): ");
    scanf("%36s", inputID);

    while (strcmp(inputID, "FIM") != 0) {
        Personagem* p = getPersonagemById(personagens, count, inputID);
        printPersonagem(p);

        printf("Enter ID (or 'FIM' to exit): ");
        scanf("%36s", inputID);
    }

    free(personagens); // Free the allocated memory
    return 0;
}
