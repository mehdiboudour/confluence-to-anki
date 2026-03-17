# Confluence → Anki Converter

A simple Java console application that converts structured Confluence pages into Anki-ready flashcards (CSV format).

## 🚀 Overview

This project allows you to transform knowledge stored in Confluence into spaced repetition flashcards.

Given a Confluence page written in a structured checklist format, the application:

* Fetches the page via the Confluence API
* Parses the structured content
* Generates CSV files compatible with Anki
* Supports both **Basic** and **Cloze** card formats

## 💡 Motivation

I built this tool to support my personal learning workflow using Anki.

In 2025, it helped me generate **4000+ flashcards** from structured notes, covering topics like:

* software engineering
* system design
* language learning
* personal development

## ⚙️ How it works

1. Provide a **Confluence page ID**
2. The app calls the **Confluence REST API**
3. It parses checklist-based structured content
4. It transforms content into:

   * Basic cards (front/back)
   * Cloze cards (fill-in-the-blank)
5. Outputs CSV files ready for Anki import

## 🧩 Input Format (Confluence)

The page should follow a **structured checklist format**, for example:

* Question: What is a load balancer?

  * Answer: A component that distributes traffic across multiple servers

or for cloze:

* A load balancer distributes traffic across {{multiple servers}}

## 📦 Output

The application generates CSV files that can be directly imported into Anki:

* `basic_cards.csv`
* `cloze_cards.csv`

## 🛠️ Tech Stack

* Java
* Confluence REST API
* CSV generation

## ▶️ Usage

```bash
# Run the application
java -jar confluence-to-anki.jar <CONFLUENCE_PAGE_ID>
```

(Adapt depending on your setup)

## 📌 Notes

* This is a personal tool and not production-hardened
* Requires access to Confluence API (authentication may be needed)
* Assumes structured content in the source page

## 🔧 Possible Improvements

* Add README examples with real input/output
* Support more flexible parsing formats
* Add configuration for different card templates
* Provide a UI instead of CLI
* Dockerize the application

## 📄 License

MIT (or whatever you prefer)

---
