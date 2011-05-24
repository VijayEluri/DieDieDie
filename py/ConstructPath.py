#!/usr/bin/env python
# -*- coding: utf-8 -*-


class ConstructPath(Action):
    """ generated source for ConstructPath

    """
    started = bool()
    finished = bool()
    dest = Point()

    @overloaded
    def __init__(self, e, xDest, yDest):
        super(ConstructPath, self).__init__(e, Point(xDest, yDest))

    @__init__.register(object, Enemy, Point)
    def __init___0(self, e, dest):
        self.reset()

    def perform(self, e):
        print "ConstructPath: perform"

    def hasStarted(self):
        return self.started

    def hasFinished(self):
        return self.finished

    def update(self, e):
        pass

    def draw(self, g):
        pass

    def reset(self):
        self.dest = None
        self.started = False
        self.finished = False


