# How we work

This is a trial for the first week. We look at it again on Monday 19 Oct and change whatever doesn't work.

## Plan

- Sprint 1, Wed 14 to Mon 19 Oct: core task - the program prints CPU, memory, PCI and USB information with vendor names.
- Sprint 2, Tue 20 to Mon 26 Oct: extra points - the extension, polish, README.
- 27 and 28 Oct: Interview practice - buffer, and practice explaining each other's code. Code freeze on 28 Oct.

Board: https://github.com/users/hyggg222/projects/5

## Roles

- Scrum Master (SM): @hyggg222
  - Tasks: Keeps the board tidy, sends reminders, helps when someone is stuck.
- Product Owner (PO): open, does anyone want it?
  - Tasks:
    - Create issues on the product backlog (github project) following the template (already created in .github/) on kickoff sprint planning. Everyone can help with this part.
    - Decides which tasks go first and checks finished work against the requirements.
- Dev: Everyone, SM and PO included, writes code.

## Scrum, the short version

- The work is split into two one-week sprints.
- The board shows every task as Backlog, Todo, In Progress, In review or Done.
- Daily Standup: Daily update before 9pm, Mon to Fri, three lines in the group chat: what I did, what I'm doing next, anything blocking me. If you are badly stuck, say it straight away.
- Sprint retro + sprint review + sprint planning: End of sprint, Mon 19 Oct after class, about 45 minutes: show what works (review), talk about what to change in the process (retro), plan the next week (planning).

## Picking a task

- Pick an issue on the board and assign yourself. One at a time.
- Everyone takes at least one core part: cpu, memory, pci, usb or report.
- Try to pick by Thu 15 Oct. If something is left, the SM suggests a name. You can still swap.

## Making a change

1. Make a branch named type(area)/issue-number-short-name, for example 'feat(cpu)/6-cpu-load'.
   - Some example type: build, feat, chore, fix, ci, etc.
2. Commit as often as you like.
   - Conventional commit:  type(area): what changed, for example 'feat(cpu): add cpu check' 
3. Open a PR, write "Closes #6" and fill in the template. Note: Following the template in '.github/'
4. Ask someone to review. main is protected, so one approval is needed (pair review).
5. After the approval, merge it yourself.

## Definition of Done 

- It builds and runs on Linux, and does not crash when a file is missing.
- There is a unit test for any parsing or calculation.
- The PR says in two or three lines how it works and which file under /proc or /sys the data comes from.
- One person approved it and can explain the code. Everyone should be able to explain any part of the program.
- It is merged into main with no conflicts.

## Stuck or late

That's fine, just say so. If an issue hasn't moved for two days, the SM will message you privately to help or split it. If it is still stuck a day later, someone else picks it up and you take a smaller one.
