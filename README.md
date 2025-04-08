# DocuGist, uno strumento per il trattamento di documenti in PDF sviluppato da [GIST](https://github.com/gvincenzi/gist)

<img src="gist.png" width="200">

## Che cos'è ?

DocuGist da la poassibilità, a partire da une URL di un PDF o da un file Markdown locale, di:
* Ottenere un riassunto del docmuento strutturato in :
    * Una sintesi di 150 caratteri
    * Un riassunto diviso in paragrafi
    * Un indice dei paragrafi generati
* Generare un quiz defindone il numero di domande

## Come funziona

DocuGist è un progetto web sviluppato in Thymeleaf 

<img src="thymeleaf.png" width="200">

che utilizza le API di Mistra AI:

<img src="mistral.png" width="200">

* [Mistral OCR API](https://mistral.ai/fr/news/mistral-ocr)
* [Mistral AI API](https://docs.mistral.ai/api/) (mistral-small-latest model)

## Contribuire
I contributi sono benvenuti! Si prega di forkare il repository e inviare una richiesta di pull con le modifiche.

## Licenza
Questo progetto è concesso in licenza con la licenza Apache. Consultare il file LICENSE per maggiori dettagli.
