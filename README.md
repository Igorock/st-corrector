ST-Corrector
=======================

Introduction
------------
This is a micro-service for word suggestion if the typo was entered

Usage
------------

Call to

http://localhost:8060/prompt?word=word_with_typo

will return

{"word": "sugggested word", success: "true/false", error: "an error if failed>"}
