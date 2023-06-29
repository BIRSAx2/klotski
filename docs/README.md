# TODO List:
- [ ] Manual
- [ ] Design Model
- [ ] Tests
- [ ] System Sequence Diagrams
- [ ] Internal Sequence Diagrams
- [ ] Class Diagrams
- [x] Use Case Diagrams
- [ ] Deployment Diagrams
- [ ] Documentation (JavaDoc)
- [x] Domain Model Diagram
- [ ] Continuous Integration (CircleCI, Github Actions)
- [ ] Code Coverage (Jacoco)
- [ ] ADD MORE


## Building and previewing site locally

### Installation Instructions for Ruby, Jekyll, and Bundler

#### Windows

1. Install Ruby:
   - Download the Ruby+Devkit installer from the RubyInstaller website (https://rubyinstaller.org/downloads/).
   - Run the installer and choose the recommended options.
   - Select the option to add Ruby executables to your system's PATH.
   - Complete the installation.
2. Install Jekyll and Bundler:

   - Open the command prompt (Windows Key + R, then type cmd).
   - Run the following command to install Jekyll and Bundler:
     ```powershell
     gem install jekyll bundler
     ```

#### macOS

1. Install Homebrew:

   - Open the Terminal application.
   - Run the following command to install Homebrew:

   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

   Follow the instructions in the Terminal to complete the installation.

2. Install Ruby:

   - In the Terminal, run the following command to install Ruby:

     ```bash
     brew install ruby
     ```

   - Install Jekyll and Bundler:

     ```bash
     gem install jekyll bundler
     ```

#### Linux (Ubuntu-based)

1. Install Ruby:

   -Open the Terminal application.
   -Run the following command to install Ruby:

   ```bash
   sudo apt update
   sudo apt install ruby-full
   ```

2. Install Jekyll and Bundler:
   ```bash
   sudo gem install jekyll bundler
   ```

#### Verify the Installation:

Run the following commands to verify if Ruby, Jekyll, and Bundler are installed correctly:

```bash
ruby --version
jekyll --version
bundler --version
```

## Build and preview the website

1.  Change your working directory to the root directory of your site.

2.  Run `bundle install`.

3.  Run `bundle exec jekyll serve` to build your site and preview it at `localhost:4000`.

The built site is stored in the directory `_site`.

## Customization

Browse the [documentation](https://just-the-docs.github.io/just-the-docs/) to learn more about how to use this theme.
