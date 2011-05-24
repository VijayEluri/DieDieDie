#!/usr/bin/env python
# -*- coding: utf-8 -*-


class Action(object):
    """ generated source for Action

    """

    def perform(self, e):
        raise NotImplementedError()

    def hasStarted(self):
        raise NotImplementedError()

    def hasFinished(self):
        raise NotImplementedError()

    def update(self, e):
        raise NotImplementedError()

    def draw(self, g):
        raise NotImplementedError()

    def reset(self):
        raise NotImplementedError()


