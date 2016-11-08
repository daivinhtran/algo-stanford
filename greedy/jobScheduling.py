class Job():
    def __init__(self, weight, length):
        self.weight = float(weight)
        self.length = float(length)
        self.diff = self.weight - self.length
        self.ratio = self.weight / self.length

    def __str__(self):
        return "diff: " + str(self.diff) + "\n" + str(self.weight)


def scheduleJobs(filename):
    file = open(filename, 'r')
    jobs = []
    num = int(file.readline())

    i = 0;
    while (i < num):
        line = file.readline()
        line = line.split(' ')
        if len(line) <= 1:
            continue

        jobs.append(Job(line[0], line[1]))
        i += 1

    sortByRatio = sorted(jobs, key = lambda x: x.ratio, reverse = True)
    sortByDiff = sorted(jobs,
        cmp = lambda x, y: cmp(x.diff, y.diff) if x.diff != y.diff else cmp(x.weight, y.weight),
        reverse = True)

    curProcessTime = 0
    sumCompleteTime = 0

    for job in sortByDiff:
        curProcessTime += job.length
        sumCompleteTime += job.weight * curProcessTime

    print("Difference:")
    print(sumCompleteTime)

    curProcessTime = 0
    sumCompleteTime = 0

    for job in sortByRatio:
        curProcessTime += job.length
        sumCompleteTime += job.weight * curProcessTime

    print("Ratio:")
    print(sumCompleteTime)


if __name__ == '__main__':
    print("Program Start")
    scheduleJobs('input6.txt')

