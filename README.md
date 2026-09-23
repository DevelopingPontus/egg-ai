# EGG

Don't crack

## The Origin

This project started with the idea around creating a tool for people with ADHD that uses AI. To help with prioritizing and estimating the required time and effort of steps toward a goal. Then it will suggest a plan of what to do and when, based on the effort of the steps and deadlines. This will hopefully help the user to stay on track and work in a sustainable way.

## Current capability

- Ask it about something and it will provide a tree structure of the topic.
- You can navigate the tree to se what each node is constructed of.
<img width="1470" height="877" alt="Screenshot 2026-09-23 at 13 43 50" src="https://github.com/user-attachments/assets/eb927037-7c71-4a2f-8ffe-c4817831bc75" />

## The Process

This is a experimental project that changes as I discover and learn.
I wanted this to work locally on consumer hardware and to be free for the user to run. Local LLM models of modest sizes struggle with larger contexts. So an idea I had was to keep each query independen of eachother and small. To do this I use a structured output format to generate an answer that can be broken down into smaller parts, that then can be broken down into smaller parts and so on.

### Web of Nodes

I started with a "universal" structure that formed a web of Nodes and the Relation. Node had a version with parameters Subject and Topic as an embedded id. It was an embedded id because if a query would produce a Node that existed in a previus web, it would connect the webs to make a larger web of Nodes. The Relation was it's own entity with parent, child and a String that said what type of relation the Nodes had.

It produced a web of Nodes but the scope of this was to wide for the local LLM model and to reasonably make something usefull with the generated data.

### Helper functions

I moved on to a new structure based on a programming concept. A usefull strategy for a programmer is to define a function and then identify helperfunctions to solve that problem. This structure I went with forms a tree of Functions. A functions has the parameters Name, Reasoning and HelperFunctions. So you query the AI and it will produce an output with a main goal as a function and defining helper functions to that main function. Depending on configuration it will itterate thru the HelperFunctions by making one query for each HelperFunction, to a set depth of the tree.

## Features

- Spring Boot 4.x application with Java 25
- Uses structured output for LLM. There is a config for LM Studio and OpenAI in the project.
- Enforces a JSON schema response shape for predictable output
- Includes retry handling for transient LLM HTTP errors

## Requirements

- Java 25
- Maven or the included Maven wrapper (`./mvnw`)
- LM Studio if you want to use a local LLM
- For OpenAI a "OPENAI_API_KEY" in a .env and configuration of what AiConfig to use in Client.

## Setup

1. Clone the repository.
2. Create a .env file with content: export OPENAI_API_KEY=your-openai-api-key
3. Add the .env file to the .gitignore file.

## API

- Endpoint: `POST /api/v1/functions`
- Expected input: prompt text
- Output: JSON object containing a nested `function`

