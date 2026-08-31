

# Tema 1 POO  - TerraBot

<div align="center"><img src="https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExdWZxaTdmdTFoczU5ZW90eTFsN2FwMG5lbDl5dDl5MHBucTB1a2NnZCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/voirD51GFZte0/giphy.gif" width="500px"></div>

#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema)


## 1. Descriere Generală

Tema implementează un simulator complet al unui ecosistem dinamic dispus pe o matrice 2D, populat cu entități diverse (plante, animale, sol, aer, apă) și un robot autonom de explorare (**TerraBot**) care navighează, scanează și influențează direct mediul.

În `main`, am păstrat doar logica de citire și parsare a comenzilor din fișierele JSON de intrare:
* **Gestiune Simulări:** Am implementat un contor dedicat pentru numărul de simulări. Fiecare sesiune este delimitată strict de comenzile `startSimulation` și `endSimulation`. Orice comandă primită în afara acestui interval este ignorată automat.
* **Popularea Hărții:** Matricea de celule este inițializată și umplută cu entități prin metoda `populMap`, după care se începe procesarea evenimentelor conform stadiului curent al simulării.

---

## 2. Ierarhia de Clase și Structura Entităților

Toate entitățile sunt modularizate în pachetul `entities`, aplicând principii solide de OOP și polimorfism:
* **`Entity` (clasa de bază abstractă):** Moștenită de clasele abstracte principale: `Plant`, `Animal`, `Soil`, `Air` și `Water`.
* **Subclase Specifice:** Fiecare tip concret de entitate extinde clasa abstractă corespunzătoare, având atribute și comportamente proprii.
* **Polimorfism și Calcul Scor:** Clasele derivate implementează metodele specifice de calcul al probabilităților (șanse de blocare, avariere a robotului etc.), în timp ce clasele părinte definesc semnăturile metodelor și logica generică de calcul a scorului de calitate.
* **Matricea Hărții (`MapA`):** Conține o matrice de obiecte de tip `Cell` (fiecare celulă gestionând tipurile de entități prezente) și metodele specifice: `populMap`, `printMapInfo` și mecanismele de interacțiune.

---

## 3. Sistemul de Interacțiuni din Mediu

Interacțiunile sunt orchestrate în 3 categorii mari, executate cronologic la fiecare pas de simulare:
1. **Interacțiuni de 1 Timestamp:** Tratează relațiile și schimburile dintre plante, sol și aer.
2. **Interacțiuni de 2 Timestamps:** Gestionează dinamica surselor și masei de apă.
3. **Interacțiunile Animalelor (Mișcare și Hrană):**
   * Fiecare celulă utilizează flag-uri booleene (de tipul `scannedX`) pentru a stabili dacă o entitate a fost scanată și dacă are dreptul să inițieze interacțiuni.
   * Clasa `Animal` deține o metodă de căutare a celei mai bune celule adiacente pe baza cerințelor din enunț.
   * Dacă animalul este de tip carnivor sau parazit, acesta poate consuma animalul din celula destinație (indiferent dacă victima este scanată sau nu).
   * Pentru hrănire, se verifică dacă resursele (plante, apă) sunt scanate, iar acțiunile alterează starea hărții (scăderea masei de apă, eliminarea plantelor, îmbunătățirea solului).
   * După deplasare, animalul este marcat ca fiind blocat pentru restul timestamp-ului curent printr-un flag boolean.

---

## 4. Logica Robotului (TerraBot)

Robotul gestionează starea internă, inventarul și resursele energetice prin metode dedicate:
* **Deplasare și Pathfinding (`moveRobot`):**
  * Robotul se deplasează pe celula vecină cu cel mai mare scor de calitate (calculat polimorfic combinând riscurile plantelor, atacurile animalelor, aerul și terenul).
  * Ordinea de verificare a vecinilor respectă direcțiile: **dreapta, jos, stânga, sus** (rezolvând orientarea axelor pe grid).
* **Managementul Energiei:** Fiecare deplasare, scanare sau acțiune consumă baterie. Dacă nivelul este insuficient, acțiunile robotului sunt blocate până la executarea comenzii `rechargeBattery`.
* **Colecții de Date:**
  * **Obiecte Scanate:** Am folosit un `ArrayList` pentru flexibilitate la inserarea și eliminarea resurselor din inventar.
  * **Facts:** Am utilizat un `LinkedHashMap` pentru a păstra ordinea exactă de inserare a faptelor învățate, esențială pentru afișarea corectă.
  * **`improve_environment`:** În urma reabilitării unei celule, elementul consumat este extras și șters din inventarul robotului.

---

## 5. Provocări Întâmpinate și Rafinamente

* **Schimbări Climatice (`changeWeather`):** Când condițiile meteorologice se modifică și este necesară afișarea unui atribut alternativ (ex. `DustParticles` $\to$ `DesertStorm`), am introdus o metodă abstractă `extraDisp` care returnează dinamic reprezentarea corectă în funcție de flag-ul de vreme activ.
* **Checkstyle & Magic Numbers:** Pentru a elimina avertismentele de stil legate de constante hardcodate, am cr
