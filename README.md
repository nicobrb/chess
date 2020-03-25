

# scacchi ![Gradle Check&Build + Image Push on GitHub Packages](https://github.com/softeng1920-inf-uniba/progetto1920-diffie/workflows/Gradle%20Check&Build%20+%20Image%20Push%20on%20GitHub%20Packages/badge.svg) ![Coverage Status](https://coveralls.io/repos/github/softeng1920-inf-uniba/progetto1920-diffie/badge.svg?branch=issue%231&t=ahjYQD)](https://coveralls.io/github/softeng1920-inf-uniba/progetto1920-diffie?branch=issue%231)

La struttura della repository si presenta nel seguente modo:
```
|-- .github
|    |-- workflows
|    |      |-- ingsw1920.yml
|-- build
|    |-- reports
|    |      |-- checkstyle
|    |      |-- findbugs
|    |      |-- jacoco/tests
|    |      |-- tests/test
|–– config
|    |–– checkstyle
|–– doc
|    |–– drawings
|    |–– javadoc 
|    |–– Report.md
|–– gradle
|–– lib
|–– res
|–– src
|    |–– main
|    |–– test
|–– .gitignore
|–– build.gradle
|–– Assegnazione progetto.md
|–– Guida per lo studente.md
|–– README.md
|–– gradlew
|–– gradle.bat
|–– settings.gradle
```

Nel seguito si dettagliano i ruoli dei diversi componenti:
- **.github/workflows/ingsw1920.yml**: dettaglia le direttive per assicurare la *continuous integration* attraverso l’uso di GitHub Actions;
- **build**: ospita la sottocartella *reports*, contenente gli output dei tool automatici di test e controllo di qualità;
- **config**: ospita i file di configurazione. L’unica configurazione di base richiesta è quella per il tool checkstyle;
- **doc**: in questa cartella deve essere inserita tutta la documentazione relativa al progetto. In particolare, in *drawings* dovranno essere salvati i diagrammi UML e *javadoc* ospiterà la documentazione generata automaticamente per il codice Java. Il file *Report.md* rappresenta la relazione finale del progetto;
- **gradle**: contiene il jar per il sistema di gestione delle dipendenze *Gradle*.
- **lib**: creata per includere eventuali *jar* di librerie esterne utilizzate dal progetto.
- **res**: la cartella deve contenere tutte le risorse usate dal sistema (immagini, testi ecc.)
- **src**: la cartella principale del progetto, in cui scrivere tutto il codice dell’applicazione. In *main* ci saranno i file sorgente e *test* conterrà i test di unità previsti.
- **.gitignore**: specifica tutti i file che devono essere esclusi dal sistema di controllo versione.
- **build.gradle**: esplicita le direttive e la configurazione per *Gradle*. 
- **Assegnazione progetto.md**: fare riferimento a questo file per la descrizione dettagliata del progetto assegnato;
- **Guida per lo studente.md:** elenca e descrive tutti i passi di configurazione necessari per attivare l’intero flusso di lavoro dietro lo sviluppo del progetto;
- **gradlew & gradlew.bat**: sono i file eseguibili di *Gradle*, rispettivamente per Unix e per Windows. Vengono generati automaticamente da Eclipse;
- **settings.gradle**: file di configurazione di *Gradle*. Anche quest’ultimo viene generato automaticamente da Eclipse.


In alcune cartelle è possibile notare la presenza di un unico file nascosto “.keep”: questo ha il solo scopo di richiedere a Git l’inclusione delle cartelle in cui è contenuto (Git esclude dal *versioning* le cartelle vuote). Pertanto, il file può essere ignorato o eventualmente cancellato nel momento in cui si inserisca almeno un altro file all’interno della cartella.
