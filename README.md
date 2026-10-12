# SalleNetwork: Algorithms and Data Structures

A Java console application from a university group project. Its four channels use separate datasets and data structures to answer questions about a fictional network.

## What it demonstrates

| Channel | Data structure and operations |
| --- | --- |
| Entrepreneurship | Directed graph, depth-first traversal, topological ordering, and shortest paths with Dijkstra. |
| Sugar | Binary search tree for citizens and searches. |
| Terror | R-tree for spatial queries and k-nearest-neighbor search. |
| Science | Custom hash table for inventions and lookup. |

The application loads the small sample datasets in `files/` at startup. The original large R-tree datasets were left out of this portfolio copy; `src/Main.java` now uses `rtreeXXS.paed` so the example works from a fresh checkout.

## Run

Requires JDK 17 or newer. From the repository root, compile the Java files under `src/` into a directory named `build`, then run `Main` with `build` on the classpath. The application displays a numbered interactive menu and reads its datasets from the relative `files/` paths.

On PowerShell:

```powershell
New-Item -ItemType Directory -Path build -Force | Out-Null
$files = Get-ChildItem src -Filter '*.java' -Recurse | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d build $files
java -cp build Main
```

A small regression check covers a k-nearest-neighbor case in which a closer monster appears after the first candidate, plus ordering and empty-result handling:

```powershell
$files = Get-ChildItem src,tests -Filter '*.java' -Recurse | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d build $files
java -cp build terror.RTreeKnnTest
```

## Portfolio cleanup

This copy preserves the team's source while omitting IDE output and large datasets. The k-nearest-neighbor routine was corrected to replace the current farthest result when a closer candidate is found; it also handles a search with no unknown monsters. The original coursework repository was not changed.

This was a group project by Laura Cuenca, Oleguer Almuni, Nil de Creus, and Alexandre Contreras. The code is presented as shared team work; this repository does not claim individual ownership of specific modules.