---
title: Notes on development
layout: default
nav_order: 8
---



This is an overview of the development approach used for creating the Klotski game. It outlines the
version control system (VCS), project management tools, and continuous integration and deployment processes utilized
during the development. The game was developed using GitHub and Git as the VCS, GitHub Issues for feature tracking and
issue management, CircleCI for testing, building, and deployment to GitHub Releases, and GitHub Actions for building the
documentation website using Jekyll and deploying it to GitHub Pages.

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
versions of the Klotski game, providing a streamlined and organized release process.

## Documentation Website with Jekyll and GitHub Pages
To document the Klotski game, we decided to use Jekyll, a popular static site generator. Jekyll allows us to create a
documentation website with ease, enabling us to provide comprehensive information about the game, its features, and how
to play.

To automate the building and deployment of the documentation website, we utilized GitHub Actions. We configured GitHub
Actions to trigger the Jekyll build process whenever changes were made to the documentation files. The generated website
was then automatically deployed to GitHub Pages, making it accessible to users through the project's GitHub repository.

## Conclusion
The development of the Klotski game followed a structured approach using GitHub and Git as the version control system,
GitHub Issues for project management, CircleCI for continuous integration and deployment, and Jekyll and GitHub Pages
for documentation. These tools and practices allowed us to collaborate effectively, maintain code quality, and provide a
user-friendly documentation website.
