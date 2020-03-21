# Assegnazione Progetto

## sna4so command
sna4so è un’applicazione di social network analysis (SNA) applicata a Stack Overflow (SO), con interfaccia a linea di comando (CLI).
* Il modello è un grafo orientato:
  - I nodi sono gli utenti
  - Gli archi diretti partono dall’utente che risponde all’utente che ha posto una domanda 
* Base di dati (sola lettura): accesso al dump del 2016/06 di Stack Overflow attraverso l'API di Google BigQuery
* Esecuzione via Docker attraverso il comando:
```bash
docker run --rm softeng1819infuniba/<nome_repository> <options>
``` 
* `options` include i seguenti parametri di input:
   - `type`: il tipo di post cercato in Stack Overflow, definito in `{question,answer,post}` (es., `type=post`)
   - `yyyy`: l'anno in cui un post è stato creato (es., `yyyy=2012`)
   - `mm`: il mese in cui un post è stato creato (es., `mm=01`)
   - `dd`: il giorno in cui un post è stato creato (es., `dd=07`)
   - `taglike`: la sottostringa da cercare nei tag applicati alle domande (es., `taglike=java` varrà per le domande taggate con `java`, `java9`, etc.)
   - `limit`: il limite al numero di risultati da restituire in una query (es., `limit=1000`)
   - `edge`: l'opzione per indicare che l'output deve includere gli archi (es., `edge=yes`); il default è output di soli nodi
   - `weight`: l'opzione per indicare che l'output relativo agli archi deve includere anche i pesi (es. `weight=yes`); il default è output di archi senza pesi
   - `user`: l'id dell'utente che ha creato un post (es. `user=86`)
 
* Al termine di ogni esecuzione di sna4so (output):
  - i risultati di una query sono salvati in un Google Spreedsheet attraverso le relative API
  - l'applicazione stampa a console l'url per acceddere via web a tale foglio di calcolo
  - il foglio di calcolo deve essere accessibile in lettura a chiunque abbia il link
  - il foglio di calcolo deve poter essere salvato in locale in formato CSV
