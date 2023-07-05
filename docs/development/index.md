---
title: Notes on development
layout: default
has_children: true
nav_order: 9
---

This is an overview of the development approach used for creating the Klotski game. It outlines the
version control system (VCS), project management tools, continuous integration and deployment processes, as well as
additional features integrated into the development workflow. The game was developed using GitHub and Git as the VCS,
GitHub Issues for feature tracking and issue management, CircleCI for testing, building, and deployment to GitHub
Releases, GitHub Actions for building the documentation website using Jekyll and deploying it to GitHub Pages, Jacoco
and JUnit for test reporting, and PlantUML for generating class diagrams.

## Version Control System (VCS)

We utilized Git as the version control system for the Klotski game development. Git allows us to track changes,
collaborate effectively, and maintain a history of the project. The project repository was hosted on GitHub, which
provides a user-friendly interface and various collaboration features.

## Project Management with GitHub Issues

GitHub Issues was our chosen project management tool to track features, tasks, and issues during the development
process. We created separate issues for each feature or bug, providing detailed descriptions, labels, and assignees to
keep track of progress and responsibilities. Using GitHub Issues helped us prioritize and organize tasks efficiently
throughout the project.

## Continuous Integration and Deployment with CircleCI

CircleCI was integrated into our development workflow to enable continuous integration, testing, and deployment. We
configured CircleCI to automatically trigger a build and run tests whenever changes were pushed to the repository. This
ensured that our codebase remained stable and functional.

For deployment, CircleCI was set up to publish artifacts to GitHub Releases. This allowed us to easily distribute new
versions of the Klotski game to users, providing a streamlined and organized release process.

## Documentation Website with Jekyll and GitHub Pages

To document the Klotski game, we decided to use Jekyll, a popular static site generator. Jekyll allows us to create a
documentation website with ease, enabling us to provide comprehensive information about the game, its features, and how
to play.

During the building of the documentation website, we incorporated test reporting using Jacoco and JUnit. This allowed us
to expose test coverage reports and test results as part of the documentation, providing transparency and insights into
the quality of the codebase.

To keep the domain design model in sync with the codebase, we configured a Gradle task called `core:
generateClassDiagrams`. This task utilizes the `io.gitlab.plunts.plantuml` Gradle plugin, which generates class diagrams
using the PlantUML markup language. These diagrams serve as visual representations of the code structure and
relationships, aiding in understanding and maintaining the codebase.

To automate the building and deployment of the documentation website, we utilized GitHub Actions. We configured GitHub
Actions to trigger the Jekyll build process whenever changes were made to the documentation files. The generated website
was then automatically deployed to GitHub Pages, making it accessible to users through the project's GitHub repository.

## Conclusion

The development of the Klotski game followed a structured approach using GitHub and Git as the version control system,
GitHub Issues for project management, CircleCI for continuous integration and deployment, Jekyll and GitHub Pages for
documentation, Jacoco and JUnit for test reporting, and PlantUML for generating class diagrams. These tools and
practices allowed us to collaborate effectively, maintain code quality, provide a user-friendly documentation website,
and ensure that the codebase and domain design model were always in sync.
