# Setup

From a fresh laptop to a running build. Takes about 20 minutes, most of it downloads.

## Where it runs

The program reads /proc and /sys, which only Linux has.

| Machine | Works? | What you see |
| --- | --- | --- |
| Linux laptop or Linux VM | Yes | Everything |
| Windows with WSL2 | Yes | Real CPU and memory. PCI shows only the virtual devices of the WSL VM. No USB. |
| alderaan | Yes | Real CPU and memory, server PCI devices, no USB |
| macOS | No | No /proc or /sys. Use alderaan or a Linux VM (for example UTM with Ubuntu). |

The starter notes say WSL2 does not provide these files. WSL2 runs a real Linux kernel, so /proc and /sys are there. What it lacks is real hardware: on a typical laptop the demo lists 4 PCI devices (Microsoft Basic Render Driver, Virtio console, Virtio file system) and /sys/bus/usb does not exist. That is fine for development, and the program has to cope with no USB anyway.

## 1. Get a Linux terminal

Windows: open PowerShell as administrator and run

    wsl --install

Restart, then open "Ubuntu" from the Start menu and pick a Linux username and password. Check that you are on WSL2:

    wsl -l -v

The VERSION column must say 2. If it says 1, run wsl --set-version Ubuntu 2.

Linux: open a terminal. macOS: log in to alderaan or start your Linux VM.

## 2. Install git and Java

    sudo apt update
    sudo apt install -y git openjdk-17-jdk
    java -version
    javac -version

Any Java from 11 up works. On alderaan, check java -version first; it may already be there.

## 3. Tell git who you are

    git config --global user.name "Your Name"
    git config --global user.email "you@example.com"

Use an email that is on your GitHub account (Settings, Emails), or the noreply address shown on that page. If the email does not match, GitHub does not count your commits as yours.

## 4. Connect to GitHub with an SSH key

GitHub no longer accepts your password for git, so make a key once per machine:

    ssh-keygen -t ed25519 -C "you@example.com"
    cat ~/.ssh/id_ed25519.pub

Press Enter at each question. Copy the line that cat prints, then on GitHub go to Settings, SSH and GPG keys, New SSH key, and paste it. Test it:

    ssh -T git@github.com

It should greet you by your GitHub username.

## 5. Clone

    cd ~
    git clone git@github.com:hyggg222/co-hardware-reporter.git
    cd co-hardware-reporter

Clone, do not fork: you have write access, and branches live in this repo.

On WSL, keep the repo in your Linux home (~), not under /mnt/c. Builds are much slower there and line endings can get mixed up.

## 6. Build and run

From the repo root:

    mkdir -p out
    javac -d out src/*.java
    java -cp out demo

On WSL2 the output looks like this:

    cores:  16
    model:  11th Gen Intel(R) Core(TM) i7-11800H @ 2.30GHz
    core 0: busy=8627 idle=1133590
    memory: total=7993244 KiB, used=1766408 KiB
    PCI devices: 4
      2680:00:00.0  1414:008e  Microsoft Corporation Basic Render Driver
      ...
    USB devices: 0

The memory total is what WSL is allowed to use, by default half of the laptop's RAM.

## 7. Editor

Any editor works. With VS Code on Windows, install the WSL extension, then run "code ." inside the repo in the Ubuntu terminal. It opens the Linux folder directly.

## 8. Day to day

    git switch main
    git pull

Then make a branch for your issue and follow CONTRIBUTING.md.

## When something goes wrong

| Message | Fix |
| --- | --- |
| javac: command not found | Step 2 |
| Permission denied (publickey) | Step 4, then check the key is on your GitHub account |
| cannot find pci.ids, or MalformedInputException | Run from the repo root, where the ids folder is |
| Commits show up without your avatar on GitHub | Step 3, the email does not match your account |
| wsl -l -v shows VERSION 1 | wsl --set-version Ubuntu 2 |
